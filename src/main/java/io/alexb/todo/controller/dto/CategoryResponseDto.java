package io.alexb.todo.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {

    private int id;
    private String category;
}
