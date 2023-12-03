package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("api/user/create-task")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.saveTask(taskDTO);
    }

    @PutMapping("api/user/edit-task/{id}")
    public TaskDTO editTask(@RequestBody TaskDTO taskDTO, @PathVariable Long idTask){
        return taskService.editTask(taskDTO, idTask);
    }

    @DeleteMapping("api/user/delete-task/{id}")
    public void deleteTask(@PathVariable Long idTask){
        taskService.deleteTask(idTask);
    }

}
