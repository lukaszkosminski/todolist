package com.todolist.service;


import com.todolist.dto.TaskCollectionDTO;
import com.todolist.dto.TaskCollectionIdDTO;
import com.todolist.dto.TaskDTO;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.*;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskCollectionRepository taskCollectionRepository;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;


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

        assertThrows(NotFoundException.class, () -> taskService.getTaskCollection(idTaskCollection, user));
    }
    @Test
    @DisplayName("Create Task  - Should return TaskDTO when TaskList found for the user")

    void createTaskShouldReturnTaskDTOWhenTaskListfoundForTheUser() throws NotFoundException {
        User user = new User();
        user.setUserId(1);

        Long idTaskCollection = 1L;

        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(1L);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStatusTask(StatusTask.TODO);
        taskDTO.setPriorityTask(PriorityTask.LOW);
        taskDTO.setDescription("test desciprion");
        taskDTO.setTitle("test title");


        when(taskCollectionRepository.findById(idTaskCollection)).thenReturn(Optional.of(taskCollection));
        TaskCollection tasklist = taskService.findTasklist(taskCollection.getId());
        tasklist.setUser(user);

        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(tasklist));


        TaskDTO result = taskService.createTask(taskDTO, idTaskCollection, user);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();


        assertNotNull(result);
        assertEquals(result,taskDTO);
        assertNotNull(savedTask.getDateTime());
    }
    @Test
    @DisplayName("Edit Task  - Should return new TaskDTO when TaskList found for the user")
    public void editTaskShouldReturnNewTaskDTOWhenTaskListFoundForTheUser() throws Exception {
        // Arrange
        User user = new User();
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setId(1L);
        Long idTask = 2L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("test description2");
        taskDTO.setDescription("test description2");
        taskDTO.setStatusTask(StatusTask.DONE);
        taskDTO.setPriorityTask(PriorityTask.HIGH);
        Task existingTask = new Task();
        existingTask.setIdTask(2L);
        existingTask.setStatusTask(StatusTask.TODO);
        existingTask.setPriorityTask(PriorityTask.LOW);
        existingTask.setDescription("test description1");
        existingTask.setTitle("test title1");
        taskCollection.setUser(user);
        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(taskCollection));

        when(taskRepository.findByIdTask(idTask)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskDTO editedTaskDTO = taskService.editTask(taskDTO, idTask, user,taskCollection.getId());

        // Assert
        assertNotNull(editedTaskDTO);

        verify(taskRepository, times(1)).findByIdTask(idTask);
        verify(taskRepository, times(1)).save(existingTask);
        assertEquals(taskDTO.getTitle(), existingTask.getTitle());
        assertEquals(taskDTO.getStatusTask(), existingTask.getStatusTask());
        assertEquals(taskDTO.getPriorityTask(), existingTask.getPriorityTask());
        assertEquals(taskDTO.getDescription(), existingTask.getDescription());
        assertNotNull(existingTask.getDateTime());

    }
    @Test
    @DisplayName("Delete Task - When TaskList found for the user")
    void deleteTaskWhenTaskListfoundForTheUser() throws Exception {
        // Arrange
        Long taskId = 1L;

        User user = new User();
        TaskCollection taskCollection = new TaskCollection(); 
        taskCollection.setId(1L);

        Task task = new Task();
        when(taskRepository.findByIdTask(taskId)).thenReturn(java.util.Optional.of(task));
        when(taskCollectionRepository.findByUser(user)).thenReturn(List.of(taskCollection));


        // Act
        taskService.deleteTask(taskId, user, taskCollection.getId());

        // Assert
        verify(taskRepository, times(1)).findByIdTask(taskId);
        verify(taskRepository, times(1)).delete(task);
    }


}