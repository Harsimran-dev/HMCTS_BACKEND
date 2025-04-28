package com.hmctsbackend.controllers;

import com.hmctsbackend.entities.Task;
import com.hmctsbackend.enums.Status;
import com.hmctsbackend.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

      
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setStatus(Status.PENDING);
    }

    @Test
    void shouldCreateTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(1L)).thenThrow(new ResourceNotFoundException("Task not found with id: 1"));

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Task"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void shouldUpdateTaskStatus() throws Exception {
        task.setStatus(Status.COMPLETED);
        when(taskService.updateTaskStatus(1L, Status.COMPLETED)).thenReturn(task);

        mockMvc.perform(put("/api/tasks/{id}/status", 1L)
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(taskService, times(1)).updateTaskStatus(1L, Status.COMPLETED);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }
}
