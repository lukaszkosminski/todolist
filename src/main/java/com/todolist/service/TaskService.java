package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListService taskListService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskListService taskListService) {
        this.taskRepository = taskRepository;
        this.taskListService = taskListService;
    }

    public TaskDTO createTask(TaskDTO taskDTO, Long taskListId) throws NotFoundException {
        TaskList taskList = taskListService.findTasklist(taskListId);
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskList(taskList);
        taskRepository.save(task);
        return taskDTO;
    }

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));;
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return TaskMapper.mapToDTO(task);
    }

    public void deleteTask(Long idTask) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        taskRepository.delete(task);
    }

}
