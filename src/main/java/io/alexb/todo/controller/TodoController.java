package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.mapper.TodoMapper;
import io.alexb.todo.model.Todo;
import io.alexb.todo.service.TodoService;
import io.alexb.todo.service.dto.TodoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TodoController implements TodoResource {

    private final TodoService todoService;
    private final TodoMapper todoMapper;


    @Override
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<Todo> todos = todoService.findAll();
        return ResponseEntity.ok(todoMapper.mapToDtoList(todos));
    }

    @Override
    public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto todoRequestDto) {
        Todo createTodo = todoService.createTodo(todoRequestDto);
        return ResponseEntity.ok(todoMapper.mapToTodoResponseDto(createTodo));
    }

    @Override
    public ResponseEntity<Void> deleteTodo(int id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TodoResponseDto>> getCategoryFilteredTodos(String category) {
        List<Todo> todos = todoService.getCategoryFilteredTodos(category);
        return ResponseEntity.ok(todoMapper.mapToDtoList(todos));
    }

    @Override
    public ResponseEntity<TodoResponseDto> changeTitle(int id, String title) {
        Todo changedTitleTodo = todoService.changeTitleTodo(id, title);
        return ResponseEntity.ok(todoMapper.mapToTodoResponseDto(changedTitleTodo));
    }

    @Override
    public ResponseEntity<TodoResponseDto> changeDescription(int id, String description) {
        Todo changeDescriptionTodo = todoService.changeDescriptionTodo(id, description);
        return ResponseEntity.ok(todoMapper.mapToTodoResponseDto(changeDescriptionTodo));
    }

    @Override
    public ResponseEntity<TodoResponseDto> changeDueDate(int id, LocalDate date) {
        Todo changeDueDate = todoService.changeDueDate(id, date);
        return ResponseEntity.ok(todoMapper.mapToTodoResponseDto(changeDueDate));
    }
}
