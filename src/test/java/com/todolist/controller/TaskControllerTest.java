package com.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.dto.*;
import com.todolist.model.PriorityTask;
import com.todolist.model.StatusTask;
import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.repository.TaskRepository;
import com.todolist.service.TaskService;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TaskController taskController;

    //    @Mock
//    private TaskService taskService;
    @MockBean
    private TaskService taskService;

    @Mock
    private Authentication authentication;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCollectionRepository taskCollectionRepository;


    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Test createTask endpoint")
    void testCreateTask() throws Exception {
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle("Example Task");
        taskCreateDTO.setPriorityTask(PriorityTask.LOW);
        taskCreateDTO.setStatusTask(StatusTask.TODO);
        taskCreateDTO.setDescription("Example description");

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTaskCollection = 1L;
        TaskCollection taskCollection = new TaskCollection();

        TaskDTO expectedTask = new TaskDTO();
        expectedTask.setIdTask(10L);
        expectedTask.setTitle("expectedTask Task");
        expectedTask.setPriorityTask(PriorityTask.LOW);
        expectedTask.setStatusTask(StatusTask.TODO);
        expectedTask.setDescription("Example description from the expected task");

        when(taskService.createTask(any(TaskCreateDTO.class), any(Long.class), any(User.class)))
                .thenReturn(expectedTask);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create-task?idTaskCollection={idTaskCollection}", idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskCreateDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idTask").value(expectedTask.getIdTask()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priorityTask").value(expectedTask.getPriorityTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedTask.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusTask").value(expectedTask.getStatusTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedTask.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value(expectedTask.getDateTime()));

        SecurityContextHolder.clearContext();


    }


    @Test
    @DisplayName("Test editTask method")
    void testEditTask() throws Exception {
        TaskEditDTO taskEditDTO = new TaskEditDTO();
        taskEditDTO.setTitle("Edited task");
        taskEditDTO.setPriorityTask(PriorityTask.LOW);
        taskEditDTO.setStatusTask(StatusTask.TODO);
        taskEditDTO.setDescription("Example description from the edited task");

        Long idTask = 10L;
        Long idTaskCollection = 1L;


        TaskDTO expectedTask = new TaskDTO();
        expectedTask.setIdTask(10L);
        expectedTask.setTitle("expectedTask Task");
        expectedTask.setPriorityTask(PriorityTask.LOW);
        expectedTask.setStatusTask(StatusTask.TODO);
        expectedTask.setDescription("Example description from the expected task");

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        when(taskService.editTask(any(TaskEditDTO.class), any(Long.class), any(User.class), any(Long.class)))
                .thenReturn(expectedTask);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/edit-task/{idTask}?idTaskCollection={idTaskCollection}", idTask, idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskEditDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idTask").value(expectedTask.getIdTask()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priorityTask").value(expectedTask.getPriorityTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedTask.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusTask").value(expectedTask.getStatusTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedTask.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value(expectedTask.getDateTime()));

        SecurityContextHolder.clearContext();

    }

    @Test
    @DisplayName("Test deleteTask method")
    void testDeleteTask() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTask = 10L;
        Long idTaskCollection = 1L;


        doNothing().when(taskService).deleteTask(any(Long.class), any(User.class), any(Long.class));


        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/delete-task/{idTask}?idTaskCollection={idTaskCollection}", idTask, idTaskCollection))

                .andExpect(status().isOk());

        verify(taskService).deleteTask(eq(idTask), any(User.class), eq(idTaskCollection));

        SecurityContextHolder.clearContext();



    }

    @Test
    @DisplayName("Test getTask method")
    void testGetTask() throws Exception {
        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTask = 10L;
        Long idTaskCollection = 1L;

        TaskDTO expectedTask = new TaskDTO();
        expectedTask.setIdTask(10L);
        expectedTask.setTitle("expectedTask Task");
        expectedTask.setPriorityTask(PriorityTask.LOW);
        expectedTask.setStatusTask(StatusTask.TODO);
        expectedTask.setDescription("Example description from the expected task");

        when(taskService.getTask(any(Long.class), any(Long.class), any(User.class))).thenReturn(expectedTask);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get-task/{idTask}?idTaskCollection={idTaskCollection}", idTask, idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idTask").value(expectedTask.getIdTask()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priorityTask").value(expectedTask.getPriorityTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedTask.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusTask").value(expectedTask.getStatusTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedTask.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value(expectedTask.getDateTime()));

        SecurityContextHolder.clearContext();


    }


    @Test
    @DisplayName("Test getTasks method")
    void testGetTasks() throws Exception {
        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTaskCollection = 1L;

        TaskDTO expectedTask1 = new TaskDTO();
        expectedTask1.setIdTask(10L);
        expectedTask1.setTitle("expectedTask Task 1");
        expectedTask1.setPriorityTask(PriorityTask.LOW);
        expectedTask1.setStatusTask(StatusTask.TODO);
        expectedTask1.setDateTime(LocalDateTime.now());
        expectedTask1.setDescription("Example description from the expected task1");

        TaskDTO expectedTask2 = new TaskDTO();
        expectedTask2.setIdTask(20L);
        expectedTask2.setTitle("expectedTask Task2");
        expectedTask2.setPriorityTask(PriorityTask.HIGH);
        expectedTask2.setStatusTask(StatusTask.DONE);
        expectedTask2.setDateTime(LocalDateTime.now());
        expectedTask2.setDescription("Example description from the expected task2");

        List<TaskDTO> expectedTaskList = List.of(expectedTask1, expectedTask2);

        when(taskService.getTasks(any(Long.class), any(User.class))).thenReturn(expectedTaskList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get-tasks?idTaskCollection={idTaskCollection}", idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idTask").value(expectedTaskList.get(0).getIdTask()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(expectedTaskList.get(0).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priorityTask").value(expectedTaskList.get(0).getPriorityTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].statusTask").value(expectedTaskList.get(0).getStatusTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expectedTaskList.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idTask").value(expectedTaskList.get(1).getIdTask()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(expectedTaskList.get(1).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].priorityTask").value(expectedTaskList.get(1).getPriorityTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].statusTask").value(expectedTaskList.get(1).getStatusTask().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(expectedTaskList.get(1).getDescription()));

        SecurityContextHolder.clearContext();


    }

    @Test
    @DisplayName("Test createTaskCollection method")
    void testCreateTaskCollection() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        TaskCollectionDTO expectedtaskCollectionDTO = new TaskCollectionDTO();
        expectedtaskCollectionDTO.setName("default");
        expectedtaskCollectionDTO.setId(2L);

        TaskCollectionCreateDTO taskCollectionCreateDTO = new TaskCollectionCreateDTO();
        taskCollectionCreateDTO.setName("default");

        when(taskService.createTaskCollection(any(TaskCollectionCreateDTO.class), any(User.class))).thenReturn(expectedtaskCollectionDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create-task-collection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskCollectionCreateDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedtaskCollectionDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedtaskCollectionDTO.getName()));

        SecurityContextHolder.clearContext();

    }

    @Test
    @DisplayName("Test editTaskCollection method")
    void testEditTaskCollection() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTaskCollection = 1L;


        TaskCollectionDTO expectedtaskCollectionDTO = new TaskCollectionDTO();
        expectedtaskCollectionDTO.setName("default");
        expectedtaskCollectionDTO.setId(2L);

        TaskCollectionEditDTO taskCollectionEditDTO = new TaskCollectionEditDTO();
        taskCollectionEditDTO.setName("edited task collection");

        when(taskService.editTaskCollection(any(TaskCollectionEditDTO.class), any(User.class), any(Long.class))).thenReturn(expectedtaskCollectionDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/edit-task-collection?idTaskCollection={idTaskCollection}",idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskCollectionEditDTO))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedtaskCollectionDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedtaskCollectionDTO.getName()));

        SecurityContextHolder.clearContext();


    }

    @Test
    @DisplayName("Test deleteTaskCollection method")
    void testDeleteTaskCollection() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        Long idTaskCollection = 1L;

        doNothing().when(taskService).deleteTask(any(Long.class), any(User.class), any(Long.class));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/delete-task-collection?idTaskCollection={idTaskCollection}", idTaskCollection))

                .andExpect(status().isOk());

        verify(taskService).deleteTaskCollection(any(User.class), eq(idTaskCollection));

        SecurityContextHolder.clearContext();

    }

    @Test
    @DisplayName("Test getTaskCollection method")
    void testGetTaskCollection() throws Exception {

        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        TaskCollectionDTO expectedtaskCollectionDTO = new TaskCollectionDTO();
        expectedtaskCollectionDTO.setName("default");
        expectedtaskCollectionDTO.setId(2L);

        Long idTaskCollection = 2L;

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(taskService.getTaskCollection(any(Long.class), any(User.class))).thenReturn(expectedtaskCollectionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get-task-collection?idTaskCollection={idTaskCollection}", idTaskCollection)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedtaskCollectionDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedtaskCollectionDTO.getName()));

        SecurityContextHolder.clearContext();


    }

    @Test
    @DisplayName("Test getTaskListCollections method")
    void testGetTaskListCollections() throws Exception {
        User user = new User();
        user.setUserName("testName");
        user.setPassword("passTest");
        user.setUserId(1L);
        user.setRole("USER");
        user.setEmail("email@test.eu");

        TaskCollectionDTO expectedtaskCollectionDTO1 = new TaskCollectionDTO();
        expectedtaskCollectionDTO1.setName("default1");
        expectedtaskCollectionDTO1.setId(2L);

        TaskCollectionDTO expectedtaskCollectionDTO2 = new TaskCollectionDTO();
        expectedtaskCollectionDTO2.setName("default2");
        expectedtaskCollectionDTO2.setId(3L);

        List<TaskCollectionDTO> expectedtaskCollectionDTOList = List.of(expectedtaskCollectionDTO1, expectedtaskCollectionDTO2);
        when(taskService.getTaskCollections(any(User.class))).thenReturn(expectedtaskCollectionDTOList);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get-task-collections")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedtaskCollectionDTOList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expectedtaskCollectionDTOList.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expectedtaskCollectionDTOList.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expectedtaskCollectionDTOList.get(1).getName()));


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