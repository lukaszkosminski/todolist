package com.todolist.controller;

import com.todolist.dto.TaskIdDTO;
import com.todolist.dto.UserDTO;
import com.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PageController {
    private final UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/list")
    public String listPage() {
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

    @PostMapping("/updateTaskStatus")
    public String changeStatus(@RequestBody TaskIdDTO taskIdDTO) {
        System.out.println(taskIdDTO.getIdTask() + " " + taskIdDTO.getStatusTask());
        return "redirect:/list";
    }

    @PostMapping("/register")
    public String addItem(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/";
    }
}