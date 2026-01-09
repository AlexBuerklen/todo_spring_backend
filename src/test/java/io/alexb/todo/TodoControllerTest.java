package io.alexb.todo;

import io.alexb.todo.controller.TodoController;
import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.mapper.TodoMapper;
import io.alexb.todo.model.Todo;
import io.alexb.todo.service.TodoService;
import io.alexb.todo.service.dto.TodoRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tools.jackson.databind.json.JsonMapper;


@WebMvcTest(controllers = TodoController.class)
class TodoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired JsonMapper jsonMapper;

    @MockitoBean TodoService todoService;
    @MockitoBean TodoMapper todoMapper;

    @Test
    @DisplayName("POST /api/todo/saveTodo -> 200 Created und Response DTO")
    void createTodo_returnsCreated_andBody() throws Exception {
        TodoRequestDto request = TodoRequestDto.builder()
                .title("Buy milk")
                .category("HOME")
                .due(LocalDate.of(2026, 1, 15))
                .description("2 liters")
                .build();

        Todo createdTodo = org.mockito.Mockito.mock(Todo.class);

        TodoResponseDto responseDto = TodoResponseDto.builder()
                .id(1)
                .title("Buy milk")
                .category("HOME")
                .due(LocalDate.of(2026, 1, 15))
                .description("2 liters")
                .build();

        given(todoService.createTodo(any(TodoRequestDto.class))).willReturn(createdTodo);
        given(todoMapper.mapToTodoResponseDto(createdTodo)).willReturn(responseDto);

        mockMvc.perform(post("/api/todo/saveTodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Buy milk"))
                .andExpect(jsonPath("$.category").value("HOME"))
                .andExpect(jsonPath("$.due").value("2026-01-15"))
                .andExpect(jsonPath("$.description").value("2 liters"));

        verify(todoService).createTodo(any(TodoRequestDto.class));
        verify(todoMapper).mapToTodoResponseDto(createdTodo);
    }

    @Test
    @DisplayName("POST /api/todo/saveTodo -> 400 Bad Request bei Validation-Fehler (title blank)")
    void createTodo_returnsBadRequest_whenTitleBlank() throws Exception {
        // title ist blank -> @NotBlank
        String invalidJson = """
            {
              "title": " ",
              "category": "HOME",
              "due": "2026-01-15",
              "description": "2 liters"
            }
            """;

        mockMvc.perform(post("/api/todo/saveTodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/todo/deleteTodo/{id} -> 204 No Content")
    void deleteTodo_returnsNoContent() throws Exception {
        int id = 42;

        mockMvc.perform(delete("/api/todo/deleteTodo/{id}", id))
                .andExpect(status().isNoContent());

        verify(todoService).deleteById(id);
    }

    @Test
    @DisplayName("GET /api/todo/getCategoryFilteredTodos/{category} -> 200 OK und Liste")
    void getCategoryFilteredTodos_returnsOk_andList() throws Exception {
        String category = "WORK";

        Todo t1 = org.mockito.Mockito.mock(Todo.class);
        Todo t2 = org.mockito.Mockito.mock(Todo.class);

        List<Todo> todos = List.of(t1, t2);

        List<TodoResponseDto> dtoList = List.of(
                TodoResponseDto.builder()
                        .id(1).title("Write report").category("WORK")
                        .due(LocalDate.of(2026, 2, 1)).description("Q1 numbers")
                        .build(),
                TodoResponseDto.builder()
                        .id(2).title("Team meeting").category("WORK")
                        .due(LocalDate.of(2026, 2, 2)).description("Prepare agenda")
                        .build()
        );

        given(todoService.getCategoryFilteredTodos(category)).willReturn(todos);
        given(todoMapper.mapToDtoList(todos)).willReturn(dtoList);

        mockMvc.perform(get("/api/todo/getCategoryFilteredTodos/{category}", category)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].category").value("WORK"))
                .andExpect(jsonPath("$[0].due").value("2026-02-01"))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(todoService).getCategoryFilteredTodos(category);
        verify(todoMapper).mapToDtoList(todos);
    }

    @Test
    @DisplayName("POST /api/todo/changeTitle/{id}/{title} -> 200 OK und DTO")
    void changeTitle_returnsOk_andBody() throws Exception {
        int id = 7;
        String title = "New title";

        Todo changed = org.mockito.Mockito.mock(Todo.class);
        TodoResponseDto responseDto = TodoResponseDto.builder()
                .id(id)
                .title(title)
                .category("WORK")
                .due(LocalDate.of(2026, 1, 10))
                .description("desc")
                .build();

        given(todoService.changeTitleTodo(id, title)).willReturn(changed);
        given(todoMapper.mapToTodoResponseDto(changed)).willReturn(responseDto);

        mockMvc.perform(post("/api/todo/changeTitle/{id}/{title}", id, title)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title));

        verify(todoService).changeTitleTodo(id, title);
        verify(todoMapper).mapToTodoResponseDto(changed);
    }

    @Test
    @DisplayName("POST /api/todo/changeDescription/{id}/{description} -> 200 OK und DTO")
    void changeDescription_returnsOk_andBody() throws Exception {
        int id = 7;
        String description = "Updated description";

        Todo changed = org.mockito.Mockito.mock(Todo.class);
        TodoResponseDto responseDto = TodoResponseDto.builder()
                .id(id)
                .title("title")
                .category("WORK")
                .due(LocalDate.of(2026, 1, 10))
                .description(description)
                .build();

        given(todoService.changeDescriptionTodo(id, description)).willReturn(changed);
        given(todoMapper.mapToTodoResponseDto(changed)).willReturn(responseDto);

        mockMvc.perform(post("/api/todo/changeDescription/{id}/{description}", id, description)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value(description));

        verify(todoService).changeDescriptionTodo(id, description);
        verify(todoMapper).mapToTodoResponseDto(changed);
    }

    @Test
    @DisplayName("POST /api/todo/changeDueDate/{id}/{date} -> 200 OK und DTO")
    void changeDueDate_returnsOk_andBody() throws Exception {
        int id = 7;
        LocalDate due = LocalDate.of(2026, 1, 31);

        Todo changed = org.mockito.Mockito.mock(Todo.class);
        TodoResponseDto responseDto = TodoResponseDto.builder()
                .id(id)
                .title("title")
                .category("WORK")
                .due(due)
                .description("desc")
                .build();

        given(todoService.changeDueDate(eq(id), eq(due))).willReturn(changed);
        given(todoMapper.mapToTodoResponseDto(changed)).willReturn(responseDto);

        mockMvc.perform(post("/api/todo/changeDueDate/{id}/{date}", id, due.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.due").value("2026-01-31"));

        verify(todoService).changeDueDate(id, due);
        verify(todoMapper).mapToTodoResponseDto(changed);
    }
}
