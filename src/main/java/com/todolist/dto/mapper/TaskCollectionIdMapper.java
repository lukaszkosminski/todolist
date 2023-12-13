package com.todolist.dto.mapper;

import com.todolist.dto.TaskCollectionIdDTO;
import com.todolist.model.TaskCollection;

public class TaskCollectionIdMapper {

    public static TaskCollection mapToTaskList(TaskCollectionIdDTO taskListIdDTO) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName(taskListIdDTO.getName());
        taskCollection.setId(taskListIdDTO.getId());
        return taskCollection;
    }

    public static TaskCollectionIdDTO mapToDTOWithId(TaskCollection taskCollection) {
        TaskCollectionIdDTO taskListIdDTO = new TaskCollectionIdDTO();
        taskListIdDTO.setName(taskCollection.getName());
        taskListIdDTO.setId(taskCollection.getId());
        return taskListIdDTO;
    }
}
