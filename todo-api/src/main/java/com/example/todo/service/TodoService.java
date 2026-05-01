package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.respitory.TodoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRespository repo;

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Optional<Todo> getById(Long id) {
        return repo.findById(id);
    }

    public List<Todo> getByStatus(boolean done) {
        return repo.findByCompleted(done);
    }

    public Todo create(Todo todo) {
        return repo.save(todo);
    }

    public Todo update(Long id, Todo updated) {
        return repo.findById(id).map(todo -> {
            todo.setTitle(updated.getTitle());
            todo.setDescription(updated.getDescription());
            todo.setCompleted(updated.isCompleted());
            return repo.save(todo);
        }).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
