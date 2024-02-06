package com.todolist.controller;


import com.todolist.dto.UserCreateDTO;
import com.todolist.dto.UserDTO;
import com.todolist.dto.mapper.UserMapper;
import com.todolist.model.User;
import com.todolist.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("Test Register User")
    public void testRegisterUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(UserMapper.userCreateDTOMapToUserDTO(userCreateDTO));

        UserDTO result = userController.registerUser(userCreateDTO);

        assertEquals(UserMapper.userCreateDTOMapToUserDTO(userCreateDTO), result);
    }

    @Test
    @DisplayName("Test Get All Users")
    public void testGetAllUser() {
        List<UserDTO> users = new ArrayList<>();

        when(userService.getUsers()).thenReturn(users);

        List<UserDTO> result = userController.getAllUser();

        assertEquals(users, result);
    }

    @Test
    @DisplayName("Test Get User by Id")
    void testGeUser() {
        long userId = 1L;
        User user = new User();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userController.getAllUser(userId);

        assertEquals(Optional.of(user), result);
    }

    @Test
    @DisplayName("Test Delete User")
    public void testDeleteUser() {
        long userId = 1L;

        userController.deleteUser(userId);

        verify(userService).deleteUser(userId);
    }

}