package com.todolist.service;

import com.todolist.dto.UserCreateDTO;
import com.todolist.dto.UserDTO;
import com.todolist.dto.mapper.UserMapper;
import com.todolist.model.User;
import com.todolist.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;


    @Autowired
    public UserService(UserRepository userRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;

    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = UserMapper.userCreateDTOMapToUser(userCreateDTO);
        user.setRole("USER");
        userRepository.save(user);
        String maskedPassword = user.getPassword().substring(0, 11) + "..." + user.getPassword().substring(user.getPassword().length() - 3);
        log.info("User saved successfully. Username: {}, Pass: {}, Email: {}", user.getUsername(), maskedPassword, user.getEmail());
        taskService.createDefaultTaskCollection(user);
        log.info("Default empty list saved for user. Username: {}", user.getUsername());
        return UserMapper.userMapToUserDTO(user);
    }

    public List<UserDTO> getUsers() {
        log.info("getAll method completed. Fetched {} users from the database.", userRepository.findAll().size());
        return UserMapper.ListUserMapToUserDTO(userRepository.findAll());
    }

    public UserDTO getUserById(long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            log.info("User found with ID {}: {}", id, userOptional.get().getUsername());
            return UserMapper.userMapToUserDTO(userOptional.get());
        } else {
            log.info("No user found with ID: {}", id);
        }
        return null;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
}