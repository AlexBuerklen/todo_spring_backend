package io.alexb.todo.repository;

import io.alexb.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository <Todo, Integer>{
    List<Todo> findByCategory_Category(String category);
}

