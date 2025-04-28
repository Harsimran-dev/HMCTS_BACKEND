package com.hmctsbackend.services;

import com.hmctsbackend.enums.Status;
import com.hmctsbackend.entities.Task;
import com.hmctsbackend.ResourceNotFoundException;
import com.hmctsbackend.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

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
    void shouldCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isEqualTo(1L);
        assertThat(createdTask.getName()).isEqualTo("Test Task");
        assertThat(createdTask.getStatus()).isEqualTo(Status.PENDING);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(1L);
        assertThat(foundTask.getName()).isEqualTo("Test Task");

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(1L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Task not found with id: 1");

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateTaskStatus() {
        task.setStatus(Status.PENDING);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTaskStatus(1L, Status.COMPLETED);

        assertThat(updatedTask.getStatus()).isEqualTo(Status.COMPLETED);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }
}
