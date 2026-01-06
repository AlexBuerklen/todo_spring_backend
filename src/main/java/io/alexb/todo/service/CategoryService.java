package io.alexb.todo.service;

import io.alexb.todo.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface CategoryService {

    @Transactional(readOnly = true)
    List<Category> findAll();
}
