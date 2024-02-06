package com.todolist.controller;

import com.todolist.dto.*;
import com.todolist.model.User;
import com.todolist.service.TaskService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("api/user/create-task")
    public TaskDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO, @RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws NotFoundException {
        return taskService.createTask(taskCreateDTO, idTaskCollection, user);
    }

    @PutMapping("api/user/edit-task/{idTask}")
    public TaskDTO editTask(@RequestBody TaskEditDTO taskEditDTO, @PathVariable Long idTask, @RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws Exception {
        return taskService.editTask(taskEditDTO, idTask, user, idTaskCollection);
    }

    @DeleteMapping("api/user/delete-task/{idTask}")
    public void deleteTask(@PathVariable Long idTask, @RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws Exception {
        taskService.deleteTask(idTask, user, idTaskCollection);
    }

    @GetMapping("api/user/get-task/{idTask}")
    public TaskDTO getTask(@PathVariable Long idTask, @RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTask(idTask, idTaskCollection, user);
    }

    @GetMapping("api/user/get-tasks")
    public List<TaskDTO> getTasks(@RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTasks(idTaskCollection, user);
    }


    @PostMapping("api/user/create-task-collection")
    public TaskCollectionDTO createTaskCollection(@RequestBody TaskCollectionCreateDTO taskCollectionCreateDTO, @AuthenticationPrincipal User user) {
        return taskService.createTaskCollection(taskCollectionCreateDTO, user);
    }

    @PutMapping("api/user/edit-task-collection")
    public TaskCollectionDTO editTaskCollection(@RequestBody TaskCollectionEditDTO taskCollectionEditDTO, @AuthenticationPrincipal User user, @RequestParam Long idTaskCollection) throws NotFoundException {
        return taskService.editTaskCollection(taskCollectionEditDTO, user, idTaskCollection);
    }

    @DeleteMapping("api/user/delete-task-collection")
    public void deleteTaskCollection(@AuthenticationPrincipal User user, @RequestParam Long idTaskCollection) throws Exception {
        taskService.deleteTaskCollection(user, idTaskCollection);
    }

    @GetMapping("api/user/get-task-collection")
    public TaskCollectionDTO getTaskCollection(@RequestParam Long idTaskCollection, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTaskCollection(idTaskCollection, user);
    }

    @GetMapping("api/user/get-task-collections")
    public List<TaskCollectionDTO> getTaskListCollections(@AuthenticationPrincipal User user) {
        return taskService.getTaskCollections(user);
    }
}
