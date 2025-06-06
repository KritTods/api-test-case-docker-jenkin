package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        List<Todo> todos = Arrays.asList(
            new Todo(1L, "Task 1", false),
            new Todo(2L, "Task 2", true)
        );

        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
    }

    @Test
    void testAddTodo() {
        Todo todo = new Todo(null, "New Task", false);
        Todo savedTodo = new Todo(1L, "New Task", false);

        when(todoRepository.save(todo)).thenReturn(savedTodo);

        Todo result = todoService.addTodo(todo);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
    }

    @Test
    void testDeleteTodoById() {
        Long id = 1L;

        todoService.deleteTodoById(id);

        verify(todoRepository, times(1)).deleteById(id);
    }
}
