package io.alexb.todo.mapper;

import io.alexb.todo.controller.dto.CategoryResponseDto;
import io.alexb.todo.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public List<CategoryResponseDto> mapToDtoListCategories(List<Category> categoryList){
        if (categoryList == null) { return null; }

        return categoryList.stream()
                .map(this::mapToCategoryResponseDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto mapToCategoryResponseDto(Category category){
        if (category == null) { return null; }

        return CategoryResponseDto.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }
}
