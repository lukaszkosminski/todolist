package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.service.TaskService;
import javassist.NotFoundException;
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
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO,@RequestParam Long idTasklist) throws NotFoundException {
        return taskService.createTask(taskDTO, idTasklist);
    }

    @PutMapping("api/user/edit-task/{idTask}")
    public TaskDTO editTask(@RequestBody TaskDTO taskDTO, @PathVariable Long idTask) throws Exception {
        return taskService.editTask(taskDTO, idTask);
    }

    @DeleteMapping("api/user/delete-task/{idTask}")
    public void deleteTask(@PathVariable Long idTask) throws Exception {
        taskService.deleteTask(idTask);
    }

}
