package com.todolist.controller;

import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import com.todolist.service.TaskService;
import com.todolist.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public PageController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("nameLists", taskService.getTaskCollections(user));
        return "home";
    }

    @GetMapping("/list/{listId}")
    public String listPage(@PathVariable Long listId, @AuthenticationPrincipal User user, Model model) throws NotFoundException {
        model.addAttribute("tasks", taskService.getTasks(listId, user));
        model.addAttribute("currentList", taskService.getTaskCollection(listId, user));
        model.addAttribute("nameLists", taskService.getTaskCollections(user));
        return "list";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String addItem(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/";
    }
}