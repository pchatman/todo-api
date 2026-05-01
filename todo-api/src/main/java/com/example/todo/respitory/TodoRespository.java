package com.example.todo.respitory;

import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRespository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompleted(boolean completed);
}