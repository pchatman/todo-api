package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    private TodoService service;

    @InjectMocks
    private TodoController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private Todo todo;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Buy groceries");
        todo.setDescription("Milk, eggs, bread");
        todo.setCompleted(false);
    }

    @Test
    void shouldGetAllTodos() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(todo));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Buy groceries"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    void shouldGetTodoById() throws Exception {
        when(service.getById(1L)).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Buy groceries"));
    }

    @Test
    void shouldReturn404WhenTodoNotFound() throws Exception {
        when(service.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTodo() throws Exception {
        when(service.create(any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Buy groceries"));
    }

    @Test
    void shouldUpdateTodo() throws Exception {
        todo.setCompleted(true);
        when(service.update(eq(1L), any(Todo.class))).thenReturn(todo);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldDeleteTodo() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFilterByStatus() throws Exception {
        when(service.getByStatus(false)).thenReturn(Arrays.asList(todo));

        mockMvc.perform(get("/api/todos/filter?completed=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false));
    }

}