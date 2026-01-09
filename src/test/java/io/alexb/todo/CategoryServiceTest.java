package io.alexb.todo;

import io.alexb.todo.model.Category;
import io.alexb.todo.repository.CategoryRepository;
import io.alexb.todo.service.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    @DisplayName("findAll() delegiert an CategoryRepository.findAll() und gibt Ergebnis zurück")
    void findAll_delegatesToRepository() {
        List<Category> expected = List.of(
                Category.builder().id(1).category("HOME").build(),
                Category.builder().id(2).category("WORK").build()
        );
        given(categoryRepository.findAll()).willReturn(expected);

        List<Category> result = categoryService.findAll();

        assertThat(result).isSameAs(expected);
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("saveCategory(newCategory) baut Category und ruft repository.save(category) auf")
    void saveCategory_buildsEntityAndSaves() {
        String newCategory = "SPORT";
        Category saved = Category.builder().id(10).category(newCategory).build();

        given(categoryRepository.save(org.mockito.ArgumentMatchers.any(Category.class))).willReturn(saved);

        Category result = categoryService.saveCategory(newCategory);

        assertThat(result).isSameAs(saved);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());

        Category toSave = captor.getValue();
        assertThat(toSave.getId()).as("Neue Category sollte noch keine ID haben").isNull();
        assertThat(toSave.getCategory()).isEqualTo(newCategory);
    }
}
