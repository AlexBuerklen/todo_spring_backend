package io.alexb.todo.service;

import io.alexb.todo.model.Category;
import io.alexb.todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theCategoryRepository){ categoryRepository = theCategoryRepository; }

    @Override
    public List<Category> findAll() { return categoryRepository.findAll(); }

    @Override
    public Category saveCategory(String newCategory) {

        Category category = Category.builder()
                .category(newCategory)
                .build();
        return categoryRepository.save(category);
    }
}
