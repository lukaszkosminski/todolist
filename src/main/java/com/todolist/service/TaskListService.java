package com.todolist.service;

import com.todolist.dto.TaskListDTO;
import com.todolist.dto.mapper.TaskListMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import com.todolist.repository.TaskListRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;
    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
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
}
