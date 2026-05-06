package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.respitory.TodoRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRespository repository;

    @InjectMocks
    private TodoService service;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Buy groceries");
        todo.setDescription("Milk, eggs, bread");
        todo.setCompleted(false);
    }

    @Test
    void shouldReturnAllTodos() {
        when(repository.findAll()).thenReturn(Arrays.asList(todo));

        List<Todo> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Buy groceries", result.get(0).getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnTodoById() {
        when(repository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> result = service.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Buy groceries", result.get().getTitle());
    }

    @Test
    void shouldReturnEmptyWhenTodoNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Todo> result = service.getById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void shouldCreateTodo() {
        when(repository.save(todo)).thenReturn(todo);

        Todo result = service.create(todo);

        assertNotNull(result);
        assertEquals("Buy groceries", result.getTitle());
        verify(repository, times(1)).save(todo);
    }

    @Test
    void shouldUpdateTodo() {
        Todo updated = new Todo();
        updated.setTitle("Buy groceries updated");
        updated.setDescription("Milk, eggs, bread, butter");
        updated.setCompleted(true);

        when(repository.findById(1L)).thenReturn(Optional.of(todo));
        when(repository.save(any(Todo.class))).thenReturn(updated);

        Todo result = service.update(1L, updated);

        assertEquals("Buy groceries updated", result.getTitle());
        assertTrue(result.isCompleted());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTodo() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.update(99L, todo));
    }

    @Test
    void shouldDeleteTodo() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldFilterByCompletedStatus() {
        when(repository.findByCompleted(true)).thenReturn(Arrays.asList());
        when(repository.findByCompleted(false)).thenReturn(Arrays.asList(todo));

        List<Todo> incomplete = service.getByStatus(false);
        List<Todo> complete = service.getByStatus(true);

        assertEquals(1, incomplete.size());
        assertEquals(0, complete.size());
    }
}