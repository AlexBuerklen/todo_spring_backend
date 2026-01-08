package io.alexb.todo.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TodoRequestDto {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotNull(message = "Due Date is mandatory")
    private LocalDate due;

    @NotBlank(message = "Description is mandatory")
    private String description;
}
