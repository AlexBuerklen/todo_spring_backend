package io.alexb.todo.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TodoResponseDto {

    private int id;
    private String title;
    private String category;
    private LocalDate due;
    private String description;
}
