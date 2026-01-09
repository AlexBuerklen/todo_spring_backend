package io.alexb.todo;

import io.alexb.todo.controller.CategoryController;
import io.alexb.todo.controller.dto.CategoryResponseDto;
import io.alexb.todo.mapper.CategoryMapper;
import io.alexb.todo.model.Category;
import io.alexb.todo.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CategoryService categoryService;

    @MockitoBean
    CategoryMapper categoryMapper;

    @Test
    @DisplayName("GET /api/category/getAllCategories -> 200 OK + Liste von CategoryResponseDto")
    void getAllCategories_returnsOk_andList() throws Exception {
        Category c1 = Category.builder().id(1).category("HOME").build();
        Category c2 = Category.builder().id(2).category("WORK").build();

        List<Category> categories = List.of(c1, c2);

        List<CategoryResponseDto> dtoList = List.of(
                CategoryResponseDto.builder().id(1).category("HOME").build(),
                CategoryResponseDto.builder().id(2).category("WORK").build()
        );

        given(categoryService.findAll()).willReturn(categories);
        given(categoryMapper.mapToDtoListCategories(categories)).willReturn(dtoList);

        mockMvc.perform(get("/api/category/getAllCategories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].category").value("HOME"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].category").value("WORK"));

        verify(categoryService).findAll();
        verify(categoryMapper).mapToDtoListCategories(categories);
    }

    @Test
    @DisplayName("POST /api/category/saveCategory/{newCategory} -> 200 OK + CategoryResponseDto")
    void addCategory_returnsOk_andBody() throws Exception {
        String newCategory = "SPORT";

        Category saved = Category.builder().id(10).category(newCategory).build();

        CategoryResponseDto responseDto = CategoryResponseDto.builder()
                .id(10)
                .category(newCategory)
                .build();

        given(categoryService.saveCategory(newCategory)).willReturn(saved);
        given(categoryMapper.mapToCategoryResponseDto(saved)).willReturn(responseDto);

        mockMvc.perform(post("/api/category/saveCategory/{newCategory}", newCategory)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.category").value("SPORT"));

        verify(categoryService).saveCategory(newCategory);
        verify(categoryMapper).mapToCategoryResponseDto(saved);
    }
}
