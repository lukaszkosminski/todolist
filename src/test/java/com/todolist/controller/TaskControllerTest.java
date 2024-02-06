package com.todolist.controller;

import com.todolist.dto.*;
import com.todolist.model.User;
import com.todolist.service.TaskService;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private Authentication authentication;

    @Test
    @DisplayName("Test createTask method")
    void testCreateTask() throws NotFoundException {
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        Long idTaskCollection = 1L;
        User user = new User();

        TaskDTO expectedTaskDTO = new TaskDTO();

        when(taskService.createTask(taskCreateDTO, idTaskCollection, user)).thenReturn(expectedTaskDTO);

        TaskDTO result = taskController.createTask(taskCreateDTO, idTaskCollection, user);

        assertEquals(expectedTaskDTO, result);

        verify(taskService, times(1)).createTask(taskCreateDTO, idTaskCollection, user);
    }

    @Test
    @DisplayName("Test editTask method")
    void testEditTask() throws Exception {
        TaskEditDTO taskEditDTO = new TaskEditDTO();
        Long idTask = 1L;
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.editTask(taskEditDTO, idTask, user, idTaskCollection)).thenReturn(new TaskDTO());

        TaskDTO result = taskController.editTask(taskEditDTO, idTask, idTaskCollection, user);

        verify(taskService, times(1)).editTask(taskEditDTO, idTask, user, idTaskCollection);
        assertEquals(new TaskDTO(), result);
    }

    @Test
    @DisplayName("Test deleteTask method")
    void testDeleteTask() throws Exception {
        Long idTask = 1L;
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);

        taskController.deleteTask(idTask, idTaskCollection, user);

        verify(taskService, times(1)).deleteTask(idTask, user, idTaskCollection);
    }

    @Test
    @DisplayName("Test getTask method")
    void testGetTask() throws Exception {
        Long idTask = 1L;
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.getTask(idTask, idTaskCollection, user)).thenReturn(new TaskDTO());

        TaskDTO result = taskController.getTask(idTask, idTaskCollection, user);

        verify(taskService, times(1)).getTask(idTask, idTaskCollection, user);
        assertEquals(new TaskDTO(), result);
    }

    @Test
    @DisplayName("Test getTasks method")
    void testGetTasks() throws Exception {
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.getTasks(idTaskCollection, user)).thenReturn(Collections.singletonList(new TaskDTO()));

        List<TaskDTO> result = taskController.getTasks(idTaskCollection, user);

        verify(taskService, times(1)).getTasks(idTaskCollection, user);
        assertEquals(Collections.singletonList(new TaskDTO()), result);
    }

    @Test
    @DisplayName("Test createTaskCollection method")
    void testCreateTaskCollection() {
        TaskCollectionCreateDTO taskCollectionCreateDTO = new TaskCollectionCreateDTO();
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.createTaskCollection(taskCollectionCreateDTO, user)).thenReturn(new TaskCollectionDTO());

        TaskCollectionDTO result = taskController.createTaskCollection(taskCollectionCreateDTO, user);

        verify(taskService, times(1)).createTaskCollection(taskCollectionCreateDTO, user);
        assertEquals(new TaskCollectionDTO(), result);
    }

    @Test
    @DisplayName("Test editTaskCollection method")
    void testEditTaskCollection() throws NotFoundException {
        TaskCollectionEditDTO taskCollectionEditDTO = new TaskCollectionEditDTO();
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.editTaskCollection(taskCollectionEditDTO, user, idTaskCollection)).thenReturn(new TaskCollectionDTO());

        TaskCollectionDTO result = taskController.editTaskCollection(taskCollectionEditDTO, user, idTaskCollection);

        verify(taskService, times(1)).editTaskCollection(taskCollectionEditDTO, user, idTaskCollection);
        assertEquals(new TaskCollectionDTO(), result);
    }

    @Test
    @DisplayName("Test deleteTaskCollection method")
    void testDeleteTaskCollection() throws Exception {
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);

        taskController.deleteTaskCollection(user, idTaskCollection);

        verify(taskService, times(1)).deleteTaskCollection(user, idTaskCollection);
    }

    @Test
    @DisplayName("Test getTaskCollection method")
    void testGetTaskCollection() throws Exception {
        Long idTaskCollection = 1L;
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.getTaskCollection(idTaskCollection, user)).thenReturn(new TaskCollectionDTO());

        TaskCollectionDTO result = taskController.getTaskCollection(idTaskCollection, user);

        verify(taskService, times(1)).getTaskCollection(idTaskCollection, user);
        assertEquals(new TaskCollectionDTO(), result);
    }

    @Test
    @DisplayName("Test getTaskListCollections method")
    void testGetTaskListCollections() {
        User user = new User();
        when(authentication.getPrincipal()).thenReturn(user);
        when(taskService.getTaskCollections(user)).thenReturn(Collections.singletonList(new TaskCollectionDTO()));

        List<TaskCollectionDTO> result = taskController.getTaskListCollections(user);

        verify(taskService, times(1)).getTaskCollections(user);
        assertEquals(Collections.singletonList(new TaskCollectionDTO()), result);
    }

}