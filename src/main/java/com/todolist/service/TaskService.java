package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return taskDTO;
    }
}
