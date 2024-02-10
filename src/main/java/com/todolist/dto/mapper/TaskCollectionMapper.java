package com.todolist.dto.mapper;

import com.todolist.dto.TaskCollectionCreateDTO;
import com.todolist.dto.TaskCollectionDTO;
import com.todolist.model.TaskCollection;

public class TaskCollectionMapper {

    public static TaskCollection taskCollectionDTOMapToTaskCollection(TaskCollectionDTO taskCollectionDTO) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName(taskCollectionDTO.getName());
        return taskCollection;
    }

    public static TaskCollectionDTO taskCollectionMapToTaskCollectionDTO(TaskCollection taskCollection) {
        TaskCollectionDTO taskCollectionDTO = new TaskCollectionDTO();
        taskCollectionDTO.setName(taskCollection.getName());
        taskCollectionDTO.setId(taskCollection.getId());
        return taskCollectionDTO;
    }

    public static TaskCollection taskCollectionCreateDTOMapToTaskCollection(TaskCollectionCreateDTO taskCollectionCreateDTO) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName(taskCollectionCreateDTO.getName());
        return taskCollection;
    }

}
