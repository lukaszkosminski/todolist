package com.todolist.service;

import com.todolist.dto.UserDTO;
import com.todolist.dto.mapper.UserMapper;
import com.todolist.model.User;
import com.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    @Autowired
    public UserService(UserRepository userRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.MapToUser(userDTO);
        user.setRole("USER");
        userRepository.save(user);
        taskService.saveDefaultEmptyList(user);
        return userDTO;
    }

    public List<UserDTO> getAll() {
        return UserMapper.ListUserMapToUserDTO(userRepository.findAll());
    }

    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}