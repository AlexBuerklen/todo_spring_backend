package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

public interface CategoryResource {

    @Operation(summary = "Get a list of categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/getAllCategories")
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();
}
