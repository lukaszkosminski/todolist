package com.todolist.dto;


import com.todolist.model.PriorityTask;
import com.todolist.model.StatusTask;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskDTO {
    private Long idTask;
    private PriorityTask priorityTask;
    private String title;
    private String description;
    private StatusTask statusTask;
    private LocalDateTime dateTime;

}
