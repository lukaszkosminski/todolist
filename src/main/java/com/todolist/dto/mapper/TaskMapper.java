package com.todolist.dto.mapper;

import com.todolist.dto.TaskDTO;
import com.todolist.model.Task;

public class TaskMapper {

    public static Task mapToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setIsCompleted(taskDTO.getIsCompleted());
        return task;
    }

    public static TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriorityTask(task.getPriorityTask());
        taskDTO.setIsCompleted(task.getIsCompleted());
        return taskDTO;
    }
}
