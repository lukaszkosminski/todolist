package com.todolist.dto.mapper;

import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserMapperTest {

    @Test
    @DisplayName("Test map UserDTO to User")
    void testMapUserDTOToUser() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testName");
        userDTO.setPassword("testPass");
        userDTO.setEmail("test@email.eu");

        User user = UserMapper.userDTOMapToUser(userDTO);

        assertEquals(userDTO.getUserName(), user.getUsername());
        assertEquals(userDTO.getEmail(), user.getEmail());

        assertTrue(passwordEncoder.matches(userDTO.getPassword(), user.getPassword().substring(8)));

    }

    @Test
    @DisplayName("Test map User to UserDTO")
    void testMapUserToUserDTO() {

        User user = new User();
        user.setUserName("testName");
        user.setEmail("test@email.eu");
        user.setPassword("testPass");

        UserDTO userDTO = UserMapper.userMapToUserDTO(user);

        assertEquals(user.getUsername(), userDTO.getUserName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    @DisplayName("Test map ListUser to ListUserDTO")
    void listUserMapToUserDTO() {

        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserName("testName1");
        user1.setEmail("test1@email.eu");
        user1.setPassword("testPass1");
        User user2 = new User();
        user2.setUserName("testName2");
        user2.setEmail("test2@email.eu");
        user2.setPassword("testPass2");
        users.add(user1);
        users.add(user2);

        List<UserDTO> result = UserMapper.ListUserMapToUserDTO(users);

        assertEquals(2, result.size());

        assertEquals(users.get(0).getUsername(), result.get(0).getUserName());
        assertEquals(users.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(users.get(0).getPassword(), result.get(0).getPassword());

        assertEquals(users.get(1).getUsername(), result.get(1).getUserName());
        assertEquals(users.get(1).getEmail(), result.get(1).getEmail());
        assertEquals(users.get(1).getPassword(), result.get(1).getPassword());
    }

}