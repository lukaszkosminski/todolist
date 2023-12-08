package com.todolist.dto.mapper;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskListDTO;
import com.todolist.model.Task;
import com.todolist.model.TaskList;

public class TaskListMapper {

    public static TaskList mapToTaskList(TaskListDTO taskListDTO) {
        TaskList taskList = new TaskList();
        taskList.setName(taskListDTO.getName());
        return taskList;
    }

    public static TaskListDTO mapToDTO(TaskList taskList) {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setName(taskList.getName());
        return taskListDTO;
    }
}
