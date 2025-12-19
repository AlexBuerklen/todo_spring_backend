package io.alexb.todo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoTitleResponseDto {
    private int id;
    private String title;
}
