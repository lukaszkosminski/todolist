package com.todolist.service;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.dto.mapper.TaskListMapper;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import com.todolist.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
