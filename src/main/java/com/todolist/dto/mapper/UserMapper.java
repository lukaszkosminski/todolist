package com.todolist.dto.mapper;

import com.todolist.dto.UserCreateDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User userDTOMapToUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static UserDTO userMapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<UserDTO> ListUserMapToUserDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setEmail(user.getEmail());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }
    public static User userCreateDTOMapToUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUserName(userCreateDTO.getUserName());
        user.setPassword(userCreateDTO.getPassword());
        user.setEmail(userCreateDTO.getEmail());
        return user;
    }
    public static UserCreateDTO userMapToUserCreateDTO(User user) {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUserName(user.getUsername());
        userCreateDTO.setPassword(user.getPassword());
        userCreateDTO.setEmail(user.getEmail());
        return userCreateDTO;
    }
    public static UserDTO userCreateDTOMapToUserDTO(UserCreateDTO userCreateDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userCreateDTO.getUserName());
        userDTO.setPassword(userCreateDTO.getPassword());
        userDTO.setEmail(userCreateDTO.getEmail());
        return userDTO;
    }
}