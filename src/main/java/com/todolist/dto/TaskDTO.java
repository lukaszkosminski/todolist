package com.todolist.dto;


import com.todolist.model.PriorityTask;
import com.todolist.model.StatusTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private PriorityTask priorityTask;
    private String title;
    private String description;
    private StatusTask statusTask;
}
