package com.todolist.service;

import com.todolist.dto.UserDTO;
import com.todolist.dto.mapper.UserMapper;
import com.todolist.model.User;
import com.todolist.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final Marker secretMarker;


    @Autowired
    public UserService(UserRepository userRepository, TaskService taskService, @Qualifier("SECRET") Marker secretMarker) {
        this.userRepository = userRepository;
        this.taskService = taskService;
        this.secretMarker = secretMarker;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.MapToUser(userDTO);
        user.setRole("USER");
        userRepository.save(user);
        log.info(secretMarker, "User saved successfully. Username: {}, Pass: {}, Email: {}", user.getUsername(), user.getPassword(), user.getEmail());
        taskService.saveDefaultTaskCollection(user);
        log.info("Default empty list saved for user. Username: {}", user.getUsername());
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        log.info("getAll method completed. Fetched {} users from the database.", userRepository.findAll().size());
        return UserMapper.ListUserMapToUserDTO(userRepository.findAll());
    }

    public Optional<User> getUserById(long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            log.info("User found with ID {}: {}", id, userOptional.get().getUsername());
        } else {
            log.info("No user found with ID: {}", id);
        }
        return userOptional;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
}