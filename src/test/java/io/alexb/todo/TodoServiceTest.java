package io.alexb.todo;

import io.alexb.todo.model.Category;
import io.alexb.todo.model.Todo;
import io.alexb.todo.repository.CategoryRepository;
import io.alexb.todo.repository.TodoRepository;
import io.alexb.todo.service.TodoServiceImpl;
import io.alexb.todo.service.dto.TodoRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    TodoServiceImpl todoService;

    @Test
    @DisplayName("findAll() delegiert an todoRepository.findAll() und gibt Liste zurück")
    void findAll_delegatesToRepository() {
        List<Todo> expected = List.of(
                Todo.builder().id(1).title("A").build(),
                Todo.builder().id(2).title("B").build()
        );
        given(todoRepository.findAll()).willReturn(expected);

        List<Todo> result = todoService.findAll();

        assertThat(result).isSameAs(expected);
        verify(todoRepository).findAll();
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("createTodo() wirft 400 BAD_REQUEST, wenn Category nicht gefunden wird")
    void createTodo_categoryNotFound_throwsBadRequest() {
        TodoRequestDto request = TodoRequestDto.builder()
                .title("Buy milk")
                .category("  UNKNOWN  ")
                .due(LocalDate.of(2026, 1, 15))
                .description("2 liters")
                .build();

        given(categoryRepository.findByCategoryIgnoreCase("UNKNOWN"))
                .willReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(
                () -> todoService.createTodo(request),
                ResponseStatusException.class
        );

        assertThat(ex.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(ex.getReason()).contains("Kategorie nicht gefunden: UNKNOWN");

        verify(categoryRepository).findByCategoryIgnoreCase("UNKNOWN");
        verifyNoInteractions(todoRepository);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("deleteById() delegiert an todoRepository.deleteById(id)")
    void deleteById_delegatesToRepository() {
        int id = 42;

        todoService.deleteById(id);

        verify(todoRepository).deleteById(id);
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("getCategoryFilteredTodos() delegiert an findByCategory_Category(category)")
    void getCategoryFilteredTodos_delegatesToRepository() {
        String category = "HOME";
        List<Todo> expected = List.of(Todo.builder().id(1).title("X").build());

        given(todoRepository.findByCategory_Category(category)).willReturn(expected);

        List<Todo> result = todoService.getCategoryFilteredTodos(category);

        assertThat(result).isSameAs(expected);
        verify(todoRepository).findByCategory_Category(category);
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeTitleTodo() setzt Titel und speichert Todo")
    void changeTitleTodo_success_updatesAndSaves() {
        int id = 7;
        Todo existing = Todo.builder().id(id).title("Old").description("D").due(LocalDate.of(2026, 1, 1)).build();

        given(todoRepository.findById(id)).willReturn(Optional.of(existing));
        given(todoRepository.save(existing)).willReturn(existing);

        Todo result = todoService.changeTitleTodo(id, "New");

        assertThat(result).isSameAs(existing);
        assertThat(existing.getTitle()).isEqualTo("New");

        verify(todoRepository).findById(id);
        verify(todoRepository).save(existing);
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeTitleTodo() wirft EntityNotFoundException, wenn Todo nicht existiert")
    void changeTitleTodo_notFound_throwsEntityNotFound() {
        int id = 7;
        given(todoRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.changeTitleTodo(id, "New"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Todo not found with id: " + id);

        verify(todoRepository).findById(id);
        verify(todoRepository, never()).save(any());
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeDescriptionTodo() setzt Description und speichert Todo")
    void changeDescriptionTodo_success_updatesAndSaves() {
        int id = 8;
        Todo existing = Todo.builder().id(id).title("T").description("Old").due(LocalDate.of(2026, 1, 1)).build();

        given(todoRepository.findById(id)).willReturn(Optional.of(existing));
        given(todoRepository.save(existing)).willReturn(existing);

        Todo result = todoService.changeDescriptionTodo(id, "Updated description");

        assertThat(result).isSameAs(existing);
        assertThat(existing.getDescription()).isEqualTo("Updated description");

        verify(todoRepository).findById(id);
        verify(todoRepository).save(existing);
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeDescriptionTodo() wirft EntityNotFoundException, wenn Todo nicht existiert")
    void changeDescriptionTodo_notFound_throwsEntityNotFound() {
        int id = 8;
        given(todoRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.changeDescriptionTodo(id, "X"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Todo not found with id: " + id);

        verify(todoRepository).findById(id);
        verify(todoRepository, never()).save(any());
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeDueDate() setzt Due-Date und speichert Todo")
    void changeDueDate_success_updatesAndSaves() {
        int id = 9;
        LocalDate newDate = LocalDate.of(2026, 2, 2);

        Todo existing = Todo.builder().id(id).title("T").description("D").due(LocalDate.of(2026, 1, 1)).build();

        given(todoRepository.findById(id)).willReturn(Optional.of(existing));
        given(todoRepository.save(existing)).willReturn(existing);

        Todo result = todoService.changeDueDate(id, newDate);

        assertThat(result).isSameAs(existing);
        assertThat(existing.getDue()).isEqualTo(newDate);

        verify(todoRepository).findById(id);
        verify(todoRepository).save(existing);
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }

    @Test
    @DisplayName("changeDueDate() wirft EntityNotFoundException, wenn Todo nicht existiert")
    void changeDueDate_notFound_throwsEntityNotFound() {
        int id = 9;
        given(todoRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.changeDueDate(id, LocalDate.of(2026, 2, 2)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Todo not found with id: " + id);

        verify(todoRepository).findById(id);
        verify(todoRepository, never()).save(any());
        verifyNoMoreInteractions(todoRepository, categoryRepository);
    }
}
