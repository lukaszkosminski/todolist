package com.todolist.controller;

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
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("api/users")
    public List<UserDTO> getAllUser() {
        return userService.getUsers();
    }

    @GetMapping("api/user/{userId}")
    public Optional<User> getAllUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("api/user/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}