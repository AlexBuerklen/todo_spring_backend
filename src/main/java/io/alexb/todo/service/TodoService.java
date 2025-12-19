package io.alexb.todo.service;

import io.alexb.todo.model.Todo;
import io.alexb.todo.service.dto.TodoRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoService {

    @Transactional
    List<Todo> findAll();

    @Transactional
    Todo createTodo(TodoRequestDto todoRequestDto);

    @Transactional
    void deleteById(int id);
}
