package io.alexb.todo.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TodoTitleRequestDto{

    private int id;

    @NotBlank(message = "Title is mandatory")
    private String title;
}
