package com.todolist.dto.mapper;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskIdDTO;
import com.todolist.model.Task;

public class TaskIdMapper {

    public static Task mapToTask(TaskIdDTO taskIdDTO) {
        Task task = new Task();
        task.setPriorityTask(taskIdDTO.getPriorityTask());
        task.setDescription(taskIdDTO.getDescription());
        task.setTitle(taskIdDTO.getTitle());
        task.setStatusTask(taskIdDTO.getStatusTask());
        task.setIdTask(taskIdDTO.getIdTask());
        return task;
    }

    public static TaskIdDTO mapToIdDTO(Task task) {
        TaskIdDTO taskIdDTO = new TaskIdDTO();
        taskIdDTO.setTitle(task.getTitle());
        taskIdDTO.setDescription(task.getDescription());
        taskIdDTO.setPriorityTask(task.getPriorityTask());
        taskIdDTO.setStatusTask(task.getStatusTask());
        taskIdDTO.setIdTask(taskIdDTO.getIdTask());
        return taskIdDTO;
    }
}
