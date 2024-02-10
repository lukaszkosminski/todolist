package com.todolist.controller;

import com.todolist.dto.TaskCollectionDTO;
import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserCreateDTO;
import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.service.TaskService;
import com.todolist.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("Should return OK status and display home view when user is not logged in")
    void shouldReturnOkStatusAndDisplayHomeViewWhenUserIsNotLoggedIn() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().size(0));


    }

    @Test
    @DisplayName("Should return OK status and display home view with name lists when user logged in")
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnOkStatusAndDisplayHomeViewWhenUserIsLoggedIn() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setId(1L);
        taskCollectionDTO.setName("default");

        List<TaskCollectionDTO> taskCollectionDTOList = List.of(taskCollectionDTO);
        when(taskCollectionRepository.findByUser(any(User.class))).thenReturn(List.of(new TaskCollection()));
        when(taskService.getTaskCollections(user)).thenReturn(taskCollectionDTOList);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("nameLists"))
                .andExpect(model().size(1));

        SecurityContextHolder.clearContext();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("ListPage method returns list view and tasks, currentList, nameLists attributes")
    void testListPage() throws Exception {
        Long listId = 1L;
        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");
        List<TaskDTO> tasksDTO = Arrays.asList(new TaskDTO(), new TaskDTO());
        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();


        when(taskService.getTasks(any(Long.class), any(User.class))).thenReturn(tasksDTO);
        when(taskService.getTaskCollection(any(Long.class), any(User.class))).thenReturn(taskCollectionDTO);
        when(taskService.getTaskCollections(any(User.class))).thenReturn(List.of(taskCollectionDTO));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        mockMvc.perform(get("/list/{listId}", listId))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attributeExists("currentList"))
                .andExpect(model().attributeExists("nameLists"))
                .andExpect(model().size(3));

        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Test loginPage method")
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().size(0));
    }
//     TODO: admin view
//    @Test
//    @DisplayName("Test adminPage method")
//    void testAdminPage() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin"))
//                .andExpect(model().size(0));
//    }

    @Test
    @DisplayName("Test registerPage method")
    void testRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().size(0));
    }

    @Test
    @DisplayName("Test addItem method")
    void testAddItem() throws Exception {

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUserName("testName");
        userCreateDTO.setEmail("email@test.eu");
        userCreateDTO.setPassword("testPass");

        mockMvc.perform(post("/register")
                        .flashAttr("userCreateDTO", userCreateDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

}
