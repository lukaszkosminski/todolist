package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.model.User;
import com.todolist.service.TaskListService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskListController {

    private final TaskListService taskListService;
    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @PostMapping("api/user/create-tasklist")
    public TaskListDTO createTaskList(@RequestBody TaskListDTO taskListDTO, @AuthenticationPrincipal User user){
        return  taskListService.createTaskList(taskListDTO, user);
    }

    @PutMapping("api/user/edit-tasklist")
    public TaskListDTO editTaskList(@RequestBody TaskListDTO taskListDTO, @AuthenticationPrincipal User user, @RequestParam Long idTasklist) throws NotFoundException {
        return  taskListService.editTaskList(taskListDTO, user, idTasklist);
    }
    @DeleteMapping("api/user/delete-tasklist")
    public void deleteTaskList(@AuthenticationPrincipal User user, @RequestParam Long idTasklist) throws Exception {
        taskListService.deleteTaskList(user, idTasklist);
    }
    @GetMapping("api/user/get-tasklist")
    public TaskListDTO getTaskList(@RequestParam Long idTaskList, @AuthenticationPrincipal User user) throws Exception{
        return taskListService.getTaskList(idTaskList,user);
    }
    @GetMapping("api/user/get-tasklist-names")
    public List<String> getTaskListNames(@AuthenticationPrincipal User user){
        return taskListService.getTaskListNames(user);
    }

}
