package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.controller.dto.TodoTitleResponseDto;
import io.alexb.todo.mapper.TodoMapper;
import io.alexb.todo.model.Todo;
import io.alexb.todo.service.TodoService;
import io.alexb.todo.service.dto.TodoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<TodoTitleResponseDto>> getTodoTitle() {
        List<Todo> todo = todoService.findAll();
        return ResponseEntity.ok(todoMapper.mapToDtoTitleList(todo));
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
}
