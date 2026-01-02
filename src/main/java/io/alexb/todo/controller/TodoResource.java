package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.TodoResponseDto;
import io.alexb.todo.model.Todo;
import io.alexb.todo.service.dto.TodoRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Operation(summary = "Get List of Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Categories", content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/api/Todo/getTodoCategories")
    ResponseEntity<List<String>> getTodoCategory();

    @Operation(summary = "Create a new todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo created successfully", content = @Content(schema = @Schema(implementation = Todo.class))),
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

    @Operation(summary = "Get summarized categories from all Todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get summarized categories", content = @Content(schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/api/Todo/getCategoryFilteredTodos/{category}")
    ResponseEntity<List<TodoResponseDto>> getCategoryFilteredTodos(@PathVariable  String category);

    @Operation(summary = "Change Title of Todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo title changed successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/api/Todo/changeTitle/{id}/{title}")
    ResponseEntity<TodoResponseDto> changeTitle(@PathVariable int id, @PathVariable String title);

    @Operation(summary = "Change Description of Todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo description changed successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @PostMapping("/api/Todo/changeDescription/{id}/{description}")
    ResponseEntity<TodoResponseDto> changeDescription(@PathVariable int id, @PathVariable String description);

    @Operation(summary = "Change Due Date of Todo")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Todo Due Date changed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/api/Todo/changeDueDate/{id}/{date}")
    ResponseEntity<TodoResponseDto> changeDueDate(@PathVariable int id, @PathVariable LocalDate date);
}
