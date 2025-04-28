package com.hmctsbackend.services;

import com.hmctsbackend.enums.Status;
import com.hmctsbackend.entities.Task;
import com.hmctsbackend.ResourceNotFoundException;
import com.hmctsbackend.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TaskService {


    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTaskStatus(Long id, Status status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
}
