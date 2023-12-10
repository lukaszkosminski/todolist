package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.dto.mapper.TaskListMapper;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import com.todolist.repository.TaskListRepository;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }


    public TaskDTO createTask(TaskDTO taskDTO, Long taskListId, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, taskListId)) {
            throw new NotFoundException("TaskList with id " + taskListId + " not found for the user");
        }
        TaskList taskList = findTasklist(taskListId);
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskList(taskList);
        taskRepository.save(task);
        return taskDTO;
    }

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask, User user, Long taskListId) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!userContainsTaskListId(user, taskListId)) {
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
        if (!userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        taskRepository.delete(task);
    }

    public void deleteTasksByTaskList(Long idTaskList, User user) throws Exception {
        if (!userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }

        List<Task> tasksToDelete = taskRepository.findByTaskListId(idTaskList);

        if (tasksToDelete.isEmpty()) {
            throw new NotFoundException("No tasks found for TaskList with id " + idTaskList);
        }

        taskRepository.deleteAll(tasksToDelete);
    }

    public TaskDTO getTask(Long idTask, Long idTaskList, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.mapToDTO(task);
    }

    public TaskListDTO saveDefaultEmptyList(User user) {
        TaskList taskList = new TaskList();
        taskList.setName("default");
        taskList.setUser(user);
        taskListRepository.save(taskList);
        return TaskListMapper.mapToDTO(taskList);
    }

    public TaskList findTasklist(Long taskListId) throws NotFoundException {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new NotFoundException("TaskList not found"));
        return taskList;
    }

    public boolean userContainsTaskListId(User user, Long taskListId) {
        return user.getTaskList().stream().anyMatch(taskList -> taskList.getId().equals(taskListId));
    }

    public TaskListDTO createTaskList(TaskListDTO taskListDTO, User user) {
        TaskList taskList = TaskListMapper.mapToTaskList(taskListDTO);
        taskList.setUser(user);
        taskListRepository.save(taskList);
        return TaskListMapper.mapToDTO(taskList);
    }

    public TaskListDTO editTaskList(TaskListDTO taskListDTO, User user, Long idTasklist) throws NotFoundException {
        if (!userContainsTaskListId(user, idTasklist)) {
            throw new NotFoundException("TaskList with id " + idTasklist + " not found for the user");
        }
        TaskList taskList = taskListRepository.findById(idTasklist).orElseThrow(() -> new NotFoundException("TaskList not found"));
        taskList.setName(taskListDTO.getName());
        taskListRepository.save(taskList);
        return TaskListMapper.mapToDTO(taskList);
    }

    public void deleteTaskList(User user, Long idTaskList) throws Exception {
        if (!userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        deleteTasksByTaskList(idTaskList, user);
        TaskList taskList = taskListRepository.findById(idTaskList).orElseThrow(() -> new NotFoundException("TaskList with id " + idTaskList + " not found"));
        taskListRepository.delete(taskList);
    }

    public TaskListDTO getTaskList(Long idTaskList, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskList)) {
            throw new NotFoundException("TaskList with id " + idTaskList + " not found for the user");
        }
        TaskList taskList = taskListRepository.findById(idTaskList).orElseThrow(() -> new NotFoundException("TaskList with id " + idTaskList + " not found"));
        return TaskListMapper.mapToDTO(taskList);
    }

    public List<String> getTaskListNames(User user) {
        List<String> taskListNames = new ArrayList<>();
        List<TaskList> taskListsByUser = taskListRepository.findByUser(user);
        if (taskListsByUser != null) {
            for (TaskList task : taskListsByUser) {
                taskListNames.add(task.getName());
            }
        }
        return taskListNames;

    }
}
