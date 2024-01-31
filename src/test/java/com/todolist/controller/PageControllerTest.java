package com.todolist.controller;

import com.todolist.dto.TaskCollectionIdDTO;
import com.todolist.dto.TaskIdDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.service.TaskService;
import com.todolist.service.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class PageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private Authentication authentication;
    @Mock
    private TaskService taskService;
    @Mock
    private TaskCollectionRepository taskCollectionRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private PageController pageController;
    @Mock
    private Model model;

    @Test
    @DisplayName("Home Page returns 'home' view")
    void homePage_ReturnsHomePageView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @DisplayName("Home Page adds 'nameLists' attribute to model")
    void homePage_AddsAttributeToModel() throws Exception {

        User mockUser = new User(/* fill in necessary details */);
        TaskCollectionIdDTO taskCollectionIdDTO = new TaskCollectionIdDTO();
        when(taskService.getTaskCollections(mockUser)).thenReturn(List.of(taskCollectionIdDTO));

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null);

        mockMvc.perform(get("/").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("nameLists"));

    }

    @Test
    @DisplayName("ListPage method returns list view and tasks, currentList, nameLists attributes")
    void testListPage() throws NotFoundException {

        Long listId = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);

        List<TaskIdDTO> tasksIdDTO = new ArrayList<>();
        tasksIdDTO.add(new TaskIdDTO());

        TaskCollectionIdDTO currentList = new TaskCollectionIdDTO();
        currentList.setId(listId);
        currentList.setName("test");

        List<TaskCollection> nameLists = new ArrayList<>();
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setUser(user);
        nameLists.add(taskCollection);
        user.setTaskCollection(nameLists);

        when(taskService.getTasks(listId, user)).thenReturn(tasksIdDTO);
        when(taskService.getTaskCollection(listId, user)).thenReturn(currentList);
        when(taskService.getTaskCollections(user)).thenReturn(List.of(currentList));

        String result = pageController.listPage(listId, user, model);

        verify(model).addAttribute("tasks", tasksIdDTO);
        verify(model).addAttribute("currentList", currentList);
        verify(model).addAttribute("nameLists", List.of(currentList));
        assertEquals("list", result);
    }

    @Test
    @DisplayName("Test loginPage method")
    void testLoginPage() {
        String result = pageController.loginPage();
        assertEquals("login", result);
    }

    @Test
    @DisplayName("Test adminPage method")
    void testAdminPage() {
        String result = pageController.adminPage();
        assertEquals("admin", result);
    }

    @Test
    @DisplayName("Test registerPage method")
    void testRegisterPage() {
        String result = pageController.registerPage();
        assertEquals("register", result);
    }

    @Test
    @DisplayName("Test addItem method")
    void testAddItem() {
        UserDTO userDTO = new UserDTO();
        String result = pageController.addItem(userDTO);

        verify(userService, times(1)).saveUser(userDTO);

        assertEquals("redirect:/", result);
    }

}
