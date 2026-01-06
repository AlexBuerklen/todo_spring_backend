package io.alexb.todo.controller;

import io.alexb.todo.controller.dto.CategoryResponseDto;
import io.alexb.todo.mapper.CategoryMapper;
import io.alexb.todo.model.Category;
import io.alexb.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController implements CategoryResource{

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categoryMapper.mapToDtoListCategories(categories));
    }
}
