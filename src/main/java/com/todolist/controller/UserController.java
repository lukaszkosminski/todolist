package com.todolist.controller;

import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import com.todolist.service.UserService;
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
    public UserDTO RegisteUser (@RequestBody UserDTO userDTO){
        return userService.saveUser(userDTO);
    }

    @GetMapping("api/users")
    public List<UserDTO> GetAllUser(){
        return userService.GetAll();
    }

    @GetMapping("api/user/{userId}")
    public Optional<User> GetAllUser(@PathVariable long userId){
        return userService.GetById(userId);
    }

    @DeleteMapping("api/user/{userId}")
    public void DeleteUser(@PathVariable long userId){
        userService.DeleteUser(userId);
    }
}