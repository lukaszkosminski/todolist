package com.todolist.service;

import com.todolist.dto.TaskListDTO;
import com.todolist.dto.mapper.TaskListMapper;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import com.todolist.repository.TaskListRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;
    private final TaskService taskService;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository, TaskService taskService) {
        this.taskListRepository = taskListRepository;
        this.taskService = taskService;
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
        taskService.deleteTasksByTaskList(idTaskList, user);
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
}
