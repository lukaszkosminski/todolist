package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListService taskListService;

    @Autowired
    public TaskService(TaskRepository taskRepository, @Lazy TaskListService taskListService) {
        this.taskRepository = taskRepository;
        this.taskListService = taskListService;
    }

    public TaskDTO createTask(TaskDTO taskDTO, Long taskListId, User user) throws NotFoundException {
        if (!taskListService.userContainsTaskListId(user, taskListId)) {
            throw new NotFoundException("TaskList with id " + taskListId + " not found for the user");
        }
        TaskList taskList = taskListService.findTasklist(taskListId);
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskList(taskList);
        taskRepository.save(task);
        return taskDTO;
    }

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask, User user, Long taskListId) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!taskListService.userContainsTaskListId(user, taskListId)) {
            throw new NotFoundException("TaskList with id " + taskListId + " not found for the user");
        }
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return TaskMapper.mapToDTO(task);
    }

    public void deleteTask(Long idTask, User user, Long idTaskList) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!taskListService.userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        taskRepository.delete(task);
    }

    public void deleteTasksByTaskList(Long idTaskList, User user) throws Exception {
        if (!taskListService.userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }

        List<Task> tasksToDelete = taskRepository.findByTaskListId(idTaskList);

        if (tasksToDelete.isEmpty()) {
            throw new NotFoundException("No tasks found for TaskList with id " + idTaskList);
        }

        taskRepository.deleteAll(tasksToDelete);
    }

    public TaskDTO getTask(Long idTask, Long idTaskList, User user) throws NotFoundException {
        if (!taskListService.userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.mapToDTO(task);
    }
}
