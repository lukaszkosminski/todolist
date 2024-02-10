package com.todolist.controller;

import com.todolist.dto.UserCreateDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import com.todolist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/user/register")
    public UserDTO registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @GetMapping("api/users")
    public List<UserDTO> getAllUser() {
        //TODO implement admin role
        return userService.getUsers();
    }

    @GetMapping("api/user/{userId}")
    public UserDTO getAllUser(@PathVariable long userId) {
        //TODO implement admin role
        return userService.getUserById(userId);
    }

    @DeleteMapping("api/user/{userId}")
    public void deleteUser(@PathVariable long userId) {
        //TODO implement admin role
        userService.deleteUser(userId);
    }
}