package com.todolist.dto.mapper;

import com.todolist.dto.TaskCreateDTO;
import com.todolist.dto.TaskDTO;
import com.todolist.model.Task;

public class TaskMapper {

    public static Task mapToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setStatusTask(taskDTO.getStatusTask());
        return task;
    }

    public static TaskDTO taskMapToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setIdTask(task.getIdTask());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriorityTask(task.getPriorityTask());
        taskDTO.setStatusTask(task.getStatusTask());
        taskDTO.setDateTime(task.getDateTime());
        return taskDTO;
    }

    public static Task taskCreateDTOMapToTask(TaskCreateDTO taskCreateDTO) {
        Task task = new Task();
        task.setPriorityTask(taskCreateDTO.getPriorityTask());
        task.setDescription(taskCreateDTO.getDescription());
        task.setTitle(taskCreateDTO.getTitle());
        task.setStatusTask(taskCreateDTO.getStatusTask());
        return task;
    }
}
