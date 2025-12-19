package io.alexb.todo.mapper;

import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.controller.dto.TodoTitleResponseDto;
import io.alexb.todo.model.Todo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {

    public List<TodoResponseDto> mapToDtoList(List<Todo> todo){
        if (todo == null){
            return null;
        }
        return todo.stream()
                .map(this::mapToTodoResponseDto)
                .collect(Collectors.toList());
    }

    public List<TodoTitleResponseDto> mapToDtoTitleList(List<Todo> todo){
        if(todo == null){
            return null;
        }

        return todo.stream()
                .map(this::mapToTodoTitleRequestDto)
                .collect(Collectors.toList());
    }

    public TodoResponseDto mapToTodoResponseDto(Todo todo){
        if(todo == null){
            return null;
        }

        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .category(todo.getCategory())
                .due(todo.getDue())
                .description(todo.getDescription())
                .build();
    }

    public TodoTitleResponseDto mapToTodoTitleRequestDto(Todo todo){
        if(todo == null){
            return null;
        }
        return TodoTitleResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .build();
    }
}
