package com.todolist.service;

import com.todolist.dto.UserDTO;
import com.todolist.dto.mapper.UserMapper;
import com.todolist.model.User;
import com.todolist.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private TaskService taskService;

    @Test
    @DisplayName("Should save user and call saveDefaultEmptyList")
    void shouldSaveUserAndCallSaveDefaultEmptyList() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testUser");
        userDTO.setPassword("testPass");
        userDTO.setEmail("test@test.eu");

        User user = UserMapper.MapToUser(userDTO);
        user.setRole("USER");
        user.setUserId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.saveUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getUserName(), result.getUserName());
        assertEquals(userDTO.getPassword(), result.getPassword());
        assertEquals("USER", user.getRole());

        verify(userRepository, times(1)).save(any(User.class));
        verify(taskService, times(1)).saveDefaultTaskCollection(any(User.class));

    }

    @Test
    @DisplayName("Should retrieve all users")
    void shouldRetrieveAllUsers() {

        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserName("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@test.com");
        user1.setRole("USER");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUserName("user2");
        user2.setPassword("password2");
        user2.setEmail("user2@test.com");
        user2.setRole("USER");

        List<User> userList = Arrays.asList(user1, user2);
        List<UserDTO> expectedUserDTOList = UserMapper.ListUserMapToUserDTO(userList);

        when(userRepository.findAll()).thenReturn(userList);
        List<UserDTO> actualUserDTOList = userService.getUsers();

        assertIterableEquals(expectedUserDTOList, actualUserDTOList);

    }

    @Test
    @DisplayName("Should return the user with the specified identifier")
    void shouldReturnTheUserWithTheSpecifiedId() {

        long userId = 1L;
        User user = new User();
        user.setUserName("testName");
        user.setPassword("testPass");
        user.setEmail("test@email.eu");
        user.setUserId(1L);
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(userId)).thenReturn(optionalUser);

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userRepository, times(1)).findById(userId);

    }

    @Test
    @DisplayName("Test deleteUser method")
    void testDeleteUserMethod() {

        long userIdToDelete = 1L;

        userService.deleteUser(userIdToDelete);

        verify(userRepository, times(1)).deleteById(userIdToDelete);

    }
}