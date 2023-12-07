package com.todolist.dto.mapper;

import com.todolist.dto.UserDTO;
import com.todolist.model.Role;
import com.todolist.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User MapToUser(UserDTO userDTO){
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static UserDTO MapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<UserDTO> ListUserMapToUserDTO(List<User> users){
        List <UserDTO> usersDTO = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(users.get(i).getUsername());
            userDTO.setPassword(users.get(i).getPassword());
            userDTO.setEmail(users.get(i).getEmail());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }
}