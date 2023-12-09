package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.model.User;
import com.todolist.service.TaskService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("api/user/create-task")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO,@RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws NotFoundException {
        return taskService.createTask(taskDTO, idTasklist, user);
    }

    @PutMapping("api/user/edit-task/{idTask}")
    public TaskDTO editTask(@RequestBody TaskDTO taskDTO, @PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        return taskService.editTask(taskDTO, idTask,user,idTasklist);
    }

    @DeleteMapping("api/user/delete-task/{idTask}")
    public void deleteTask(@PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        taskService.deleteTask(idTask,user,idTasklist);
    }

    @GetMapping("api/user/get-task/{idTask}")
    public TaskDTO getTask(@PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception{
       return taskService.getTask(idTask,idTasklist,user);
    }
}
