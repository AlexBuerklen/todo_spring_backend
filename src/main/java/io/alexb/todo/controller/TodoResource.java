package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.controller.dto.TodoTitleResponseDto;
import io.alexb.todo.model.Todo;
import io.alexb.todo.service.dto.TodoRequestDto;
import io.alexb.todo.service.dto.TodoTitleRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface TodoResource {

    @Operation(summary = "Get all todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of todos", content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/api/Todo/getAllTodos")
    ResponseEntity<List<TodoResponseDto>> getAllTodos();

    @Operation(summary = "Get only Todos with title and id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Title of Todo", content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/api/Todo/getTodoTitle")
    ResponseEntity<List<TodoTitleResponseDto>> getTodoTitle();

    @Operation(summary = "Create a new todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo created successfully",
                    content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "Invalid todo request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/api/Todo/saveTodo")
    ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto todoRequestDto);

    @Operation(summary = "Delete a todo by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/api/Todo/deleteTodo/{id}")
    ResponseEntity<Void> deleteTodo(@PathVariable int id);
}
