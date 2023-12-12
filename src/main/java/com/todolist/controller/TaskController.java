package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskIdDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.dto.TaskListIdDTO;
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
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws NotFoundException {
        return taskService.createTask(taskDTO, idTasklist, user);
    }

    @PutMapping("api/user/edit-task/{idTask}")
    public TaskDTO editTask(@RequestBody TaskDTO taskDTO, @PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        return taskService.editTask(taskDTO, idTask, user, idTasklist);
    }

    @DeleteMapping("api/user/delete-task/{idTask}")
    public void deleteTask(@PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        taskService.deleteTask(idTask, user, idTasklist);
    }

    @GetMapping("api/user/get-task/{idTask}")
    public TaskDTO getTask(@PathVariable Long idTask, @RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTask(idTask, idTasklist, user);
    }

    @GetMapping("api/user/get-tasks")
    public List<TaskIdDTO> getTasks(@RequestParam Long idTasklist, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTasks(idTasklist, user);
    }


    @PostMapping("api/user/create-tasklist")
    public TaskListIdDTO createTaskList(@RequestBody TaskListDTO taskListDTO, @AuthenticationPrincipal User user) {
        return taskService.createTaskList(taskListDTO, user);
    }

    @PutMapping("api/user/edit-tasklist")
    public TaskListDTO editTaskList(@RequestBody TaskListDTO taskListDTO, @AuthenticationPrincipal User user, @RequestParam Long idTasklist) throws NotFoundException {
        return taskService.editTaskList(taskListDTO, user, idTasklist);
    }

    @DeleteMapping("api/user/delete-tasklist")
    public void deleteTaskList(@AuthenticationPrincipal User user, @RequestParam Long idTasklist) throws Exception {
        taskService.deleteTaskList(user, idTasklist);
    }

    @GetMapping("api/user/get-tasklist")
    public TaskListIdDTO getTaskList(@RequestParam Long idTaskList, @AuthenticationPrincipal User user) throws Exception {
        return taskService.getTaskList(idTaskList, user);
    }

    @GetMapping("api/user/get-tasklist-list")
    public List<TaskListIdDTO> getTaskListNames(@AuthenticationPrincipal User user) {
        return taskService.getTaskListsIdDTO(user);
    }
}
