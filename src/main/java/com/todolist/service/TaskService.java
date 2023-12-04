package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask) throws Exception {
        Optional<Task> taskOptional = taskRepository.findByIdTask(idTask);
        Task task = getTaskOrThrow(taskOptional, idTask);
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return TaskMapper.mapToDTO(task);
    }

    public void deleteTask(Long idTask) throws Exception {
        Optional<Task> task = taskRepository.findByIdTask(idTask);
        Task taskOrThrow = getTaskOrThrow(task,idTask);
        taskRepository.delete(taskOrThrow);
    }
    private Task getTaskOrThrow(Optional<Task> taskOptional, Long idTask) throws Exception {
         return taskOptional.orElseThrow(() -> new Exception("Task " + idTask + " not found"));
    }
}
