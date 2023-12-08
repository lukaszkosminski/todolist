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
    private final TaskListService taskListService;

    @Autowired
    public UserService(UserRepository userRepository, TaskListService taskListService) {
        this.userRepository = userRepository;
        this.taskListService = taskListService;
    }

    public UserDTO saveUser(UserDTO userDTO){
        User user = UserMapper.MapToUser(userDTO);
        user.setRole("USER");
        userRepository.save(user);
        taskListService.saveDefaultEmptyList(user);
        return userDTO;
    }

    public List<UserDTO> GetAll() {
        return UserMapper.ListUserMapToUserDTO(userRepository.findAll());
    }

    public Optional<User> GetById(long id) {
        return userRepository.findById(id);
    }

    public void DeleteUser (long id) {
        userRepository.deleteById(id);
    }
}