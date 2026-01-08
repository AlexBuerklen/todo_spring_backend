package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface CategoryResource {

    @Operation(summary = "Get a list of categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/getAllCategories")
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();

    @Operation(summary = "Add category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add category"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/saveCategory/{newCategory}")
    ResponseEntity<CategoryResponseDto> addCategory(@PathVariable  String newCategory);
}
