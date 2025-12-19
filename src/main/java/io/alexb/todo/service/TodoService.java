package io.alexb.todo.service;

import io.alexb.todo.model.Todo;
import io.alexb.todo.service.dto.TodoRequestDto;
import io.alexb.todo.service.dto.TodoTitleRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    @Transactional
    List<Todo> findAll();

    @Transactional
    Todo createTodo(TodoRequestDto todoRequestDto);

    @Transactional
    void deleteById(int id);
}
