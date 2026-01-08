package io.alexb.todo.service;

import io.alexb.todo.model.Category;
import io.alexb.todo.model.Todo;
import io.alexb.todo.repository.CategoryRepository;
import io.alexb.todo.repository.TodoRepository;
import io.alexb.todo.service.dto.TodoRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import static io.alexb.todo.exception.TodoValidationException.validateId;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<Todo> findAll() { return todoRepository.findAll(); }

    @Override
    public Todo createTodo(TodoRequestDto todoRequestDto) {

        Category category = categoryRepository
                .findByCategoryIgnoreCase(todoRequestDto.getCategory().trim())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Kategorie nicht gefunden: " + todoRequestDto.getCategory().trim()));

        Todo todo = Todo.builder()
                .title(todoRequestDto.getTitle())
                .category(category)
                .due(todoRequestDto.getDue())
                .description(todoRequestDto.getDescription())
                .build();

        return todoRepository.save(todo);
    }

    @Override
    public void deleteById(int id) { todoRepository.deleteById(id); }

    @Override
    public List<Todo> getCategoryFilteredTodos(String category) {
        return todoRepository.findByCategory_Category(category);
    }

    @Override
    public Todo changeTitleTodo(int id, String title) {
        validateId(id);

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));

        existingTodo.setTitle(title);
        return todoRepository.save(existingTodo);
    }

    @Override
    public Todo changeDescriptionTodo(int id, String description) {
        validateId(id);

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo not found with id: "+ id));

        existingTodo.setDescription(description);
        return todoRepository.save(existingTodo);
    }

    @Override
    public Todo changeDueDate(int id, LocalDate date) {
        validateId(id);

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));

        existingTodo.setDue(date);
        return todoRepository.save(existingTodo);
    }
}
