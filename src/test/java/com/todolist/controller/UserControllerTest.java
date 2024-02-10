package com.todolist.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.dto.UserCreateDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import com.todolist.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Test Register User")
    public void testRegisterUser() throws Exception {

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUserName("testName");
        userCreateDTO.setEmail("test@emai.eu");
        userCreateDTO.setPassword("testPass");

        UserDTO expectedUser = new UserDTO();
        expectedUser.setUserId(1L);
        expectedUser.setUserName("testName");
        expectedUser.setEmail("test@emai.eu");
        expectedUser.setPassword("testPass");
        expectedUser.setRole("USER");

        when(userService.createUser(userCreateDTO)).thenReturn(expectedUser);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userCreateDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(expectedUser.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(expectedUser.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(expectedUser.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(expectedUser.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedUser.getEmail()));

    }

    @Test
    @DisplayName("Test Get All Users")
    public void testGetAllUser() throws Exception {
        //TODO implement admin role

        User user = new User();

        UserDTO expectedUser1 = new UserDTO();
        expectedUser1.setUserId(1L);
        expectedUser1.setUserName("testName1");
        expectedUser1.setEmail("test1@email.eu");
        expectedUser1.setPassword("testPass1");
        expectedUser1.setRole("USER");

        UserDTO expectedUser2 = new UserDTO();
        expectedUser2.setUserId(2L);
        expectedUser2.setUserName("testName1");
        expectedUser2.setEmail("test1@email.eu");
        expectedUser2.setPassword("testPass1");
        expectedUser2.setRole("USER");

        List<UserDTO> expectedUserList = List.of(expectedUser1, expectedUser2);

        when(userService.getUsers()).thenReturn(expectedUserList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(expectedUserList.get(0).getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value(expectedUserList.get(0).getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(expectedUserList.get(0).getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value(expectedUserList.get(0).getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(expectedUserList.get(0).getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(expectedUserList.get(1).getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userName").value(expectedUserList.get(1).getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value(expectedUserList.get(1).getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role").value(expectedUserList.get(1).getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value(expectedUserList.get(1).getEmail()));

        SecurityContextHolder.clearContext();


    }

    @Test
    @DisplayName("Test Get User by Id")
    void testGeUser() throws Exception {
        //TODO implement admin role

        Long userId = 1L;
        User user = new User();


        UserDTO expectedUser = new UserDTO();
        expectedUser.setUserId(1L);
        expectedUser.setUserName("testName1");
        expectedUser.setEmail("test1@email.eu");
        expectedUser.setPassword("testPass1");
        expectedUser.setRole("USER");

        when(userService.getUserById(any(Long.class))).thenReturn(expectedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(expectedUser.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(expectedUser.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(expectedUser.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(expectedUser.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedUser.getEmail()));

        SecurityContextHolder.clearContext();

    }

    @Test
    @DisplayName("Test Delete User")
    public void testDeleteUser() throws Exception {
        //TODO implement admin role

        Long userId = 1L;
        User user = new User();

        doNothing().when(userService).deleteUser(any(Long.class));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{userId}", userId))

                .andExpect(status().isOk());

        verify(userService).deleteUser(eq(userId));
        SecurityContextHolder.clearContext();


    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}