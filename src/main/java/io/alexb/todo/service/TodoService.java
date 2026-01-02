package io.alexb.todo.service;

import io.alexb.todo.model.Todo;
import io.alexb.todo.service.dto.TodoRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {

    @Transactional(readOnly = true)
    List<Todo> findAll();

    @Transactional
    Todo createTodo(TodoRequestDto todoRequestDto);

    @Transactional
    void deleteById(int id);

    @Transactional(readOnly = true)
    List<String> findAllWithFilteredCategories();

    @Transactional(readOnly = true)
    List<Todo> getCategoryFilteredTodos(String category);

    @Transactional
    Todo changeTitleTodo(int id, String title);

    @Transactional
    Todo changeDescriptionTodo(int id, String description);

    @Transactional
    Todo changeDueDate(int id, LocalDate date);
}
