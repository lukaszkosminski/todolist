package com.todolist.dto.mapper;

import com.todolist.dto.TaskCollectionDTO;
import com.todolist.model.TaskCollection;

public class TaskCollectionMapper {

    public static TaskCollection mapToTaskList(TaskCollectionDTO taskCollectionDTO) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName(taskCollectionDTO.getName());
        return taskCollection;
    }

    public static TaskCollectionDTO mapToDTO(TaskCollection taskCollection) {
        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setName(taskCollection.getName());
        return taskCollectionDTO;
    }
}
