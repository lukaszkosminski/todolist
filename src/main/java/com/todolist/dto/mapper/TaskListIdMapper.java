package com.todolist.dto.mapper;

import com.todolist.dto.TaskListIdDTO;
import com.todolist.model.TaskList;

public class TaskListIdMapper {

    public static TaskList mapToTaskList(TaskListIdDTO taskListIdDTO) {
        TaskList taskList = new TaskList();
        taskList.setName(taskListIdDTO.getName());
        taskList.setId(taskListIdDTO.getId());
        return taskList;
    }

    public static TaskListIdDTO mapToDTOWithId(TaskList taskList) {
        TaskListIdDTO taskListIdDTO = new TaskListIdDTO();
        taskListIdDTO.setName(taskList.getName());
        taskListIdDTO.setId(taskList.getId());
        return taskListIdDTO;
    }
}
