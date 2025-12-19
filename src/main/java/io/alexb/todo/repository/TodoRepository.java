package io.alexb.todo.repository;

import io.alexb.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository <Todo, Integer>{
    @Query("select c.id as id, c.title as category from Todo c order by c.id")
    List<Todo> findAllByCategoryAndId();
}

