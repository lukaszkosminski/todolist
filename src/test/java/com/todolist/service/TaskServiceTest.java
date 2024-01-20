package com.todolist.service;


import com.todolist.dto.TaskCollectionDTO;
import com.todolist.dto.TaskCollectionIdDTO;
import com.todolist.model.Task;
import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCollectionRepository taskCollectionRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    @DisplayName("Should save default collection with empty list")
    public void shouldSaveDefaultCollection() {
        User user = new User();

        TaskCollectionDTO result = taskService.saveDefaultEmptyList(user);

        assertNotNull(result);
        assertEquals("default", result.getName());
    }

    @Test
    @DisplayName("Should return TaskCollection when found - find task collection")
    void shouldReturnTaskCollectionWhenFound() throws NotFoundException {

        Long idTaskCollection = 1L;

        TaskCollection expectedTaskCollection = new TaskCollection();
        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.of(expectedTaskCollection));

        TaskCollection resultTaskCollection = taskService.findTasklist(idTaskCollection);

        assertNotNull(resultTaskCollection);
        assertEquals(expectedTaskCollection, resultTaskCollection);
    }

    @Test
    @DisplayName("Should throw NotFoundException when not found - find task collection")
    void ShouldThrowNotFoundExceptionWhenNotFound() {

        Long idTaskCollection = 1L;

        TaskCollectionRepository taskCollectionRepository = mock(TaskCollectionRepository.class);
        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.findTasklist(idTaskCollection));
    }

    @Test
    @DisplayName("User contains collectionList ID - Should return true if ID found")
    void userContainsTaskListId_ShouldReturnTrueIfIdFound() {

        User user = new User();
        Long idTaskCollection = 1L;
        List<TaskCollection> taskListByUser = new ArrayList<>();
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(idTaskCollection);
        taskListByUser.add(taskCollection);

        when(taskCollectionRepository.findByUser(user)).thenReturn(taskListByUser);

        boolean result = taskService.userContainsTaskListId(user, idTaskCollection);

        assertTrue(result);
    }

    @Test
    @DisplayName("User Contains TaskList ID - Should return false if ID not found")
    void userContainsTaskListId_ShouldReturnFalseIfIdNotFound() {

        User user = new User();
        Long idTaskCollection = 1L;

        TaskCollectionRepository taskCollectionRepository = mock(TaskCollectionRepository.class);
        when(taskCollectionRepository.findByUser(user)).thenReturn(Collections.emptyList());

        boolean result = taskService.userContainsTaskListId(user, idTaskCollection);

        assertFalse(result);
    }

    @Test
    @DisplayName("Create Task Collection - Should return DTO with ID after creation")
    void createTaskCollection_ShouldReturnDtoWithIdAfterCreation() {

        User user = new User();
        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setName("Test Collection");

        when(taskCollectionRepository.save(any(TaskCollection.class))).thenAnswer(invocation -> {
            TaskCollection savedTaskCollection = invocation.getArgument(0);
            savedTaskCollection.setId(1L);
            return savedTaskCollection;
        });

        // When
        TaskCollectionIdDTO resultDTO = taskService.createTaskCollection(taskCollectionDTO, user);

        // Then
        assertNotNull(resultDTO);
        assertNotNull(resultDTO.getId());
        assertEquals("Test Collection", resultDTO.getName());
    }

    @Test
    @DisplayName("Edit Task Collection - Should return edited DTO after successful edit")
    void editTaskCollection_ShouldReturnEditedDtoAfterSuccessfulEdit() throws NotFoundException {

        User user = new User();
        user.setUserId(1);
        Long idTaskCollection = 1L;

        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setName("Edited Collection");

        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(1L);

        user.setTaskCollection(List.of(taskCollection));
        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(taskCollection));

        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.of(taskCollection));

        TaskCollectionDTO resultDTO = taskService.editTaskCollection(taskCollectionDTO, user, idTaskCollection);

        assertNotNull(resultDTO);
        assertEquals("Edited Collection", resultDTO.getName());
    }

    @Test
    @DisplayName("Edit Task Collection - Should throw NotFoundException when TaskList not found")
    void editTaskCollection_ShouldThrowNotFoundExceptionWhenTaskListNotFound() {
        // Given
        User user = new User();
        Long idTaskCollection = 1L;
        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setName("Edited Collection");

        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.editTaskCollection(taskCollectionDTO, user, idTaskCollection));
    }

    @Test
    @DisplayName("Delete Task Collection - Should delete collection and associated tasks")
    void deleteTaskCollection_ShouldDeleteCollectionAndTasks() throws Exception {
        // Given
        User user = new User();
        user.setUserId(1L);
        Long idTaskCollection = 1L;
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(idTaskCollection);
        user.setTaskCollection(List.of(taskCollection));
        Task task = new Task();
        taskCollection.setTask(List.of(task));
        task.setTaskCollection(taskCollection);

        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(taskCollection));
        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.of(taskCollection));

        assertThrows(NotFoundException.class, () -> taskService.deleteTaskCollection(user, idTaskCollection));

    }

    @Test
    @DisplayName("Delete Task Collection - Should throw NotFoundException when TaskList not found")
    void deleteTaskCollection_ShouldThrowNotFoundExceptionWhenTaskListNotFound() {

        User user = new User();
        Long idTaskCollection = 1L;

        assertThrows(NotFoundException.class, () -> taskService.deleteTaskCollection(user, idTaskCollection));
    }

    @Test
    @DisplayName("Get Task Collection - Should return DTO with ID when TaskList found for the user")
    void getTaskCollection_ShouldReturnDtoWithIdWhenTaskListFoundForUser() throws NotFoundException {
        // Given
        User user = new User();
        Long idTaskCollection = 1L;

        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(idTaskCollection);
        user.setTaskCollection(List.of(taskCollection));
        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(taskCollection));
        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.of(taskCollection));

        TaskCollectionIdDTO resultDTO = taskService.getTaskCollection(idTaskCollection, user);

        assertNotNull(resultDTO);
        assertEquals(idTaskCollection, resultDTO.getId());
    }

    @Test
    @DisplayName("Get Task Collection - Should throw NotFoundException when TaskList not found for the user")
    void getTaskCollection_ShouldThrowNotFoundExceptionWhenTaskListNotFoundForUser() {

        User user = new User();
        Long idTaskCollection = 1L;

        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(1L);

        when(taskCollectionRepository.findByUser(user)).thenReturn(Collections.emptyList());
        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> taskService.getTaskCollection(idTaskCollection, user));
    }

}