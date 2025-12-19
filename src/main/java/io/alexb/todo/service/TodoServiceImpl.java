package io.alexb.todo.service;

import io.alexb.todo.exception.TodoValidationException;
import io.alexb.todo.model.Todo;
import io.alexb.todo.repository.TodoRepository;
import io.alexb.todo.service.dto.TodoRequestDto;
import io.alexb.todo.service.dto.TodoTitleRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository theTodoRepository) { todoRepository = theTodoRepository;}

    @Override
    public List<Todo> findAll() { return todoRepository.findAll(); }

    @Override
    public Todo createTodo(TodoRequestDto todoRequestDto) {

        TodoValidationException.validateId(todoRequestDto.getId());

        Todo todo = Todo.builder()
                .id(todoRequestDto.getId())
                .title(todoRequestDto.getTitle())
                .category(todoRequestDto.getCategory())
                .due(todoRequestDto.getDue())
                .description(todoRequestDto.getDescription())
                .build();

        return todoRepository.save(todo);
    }

    @Override
    public void deleteById(int id) { todoRepository.deleteById(id); }

}
