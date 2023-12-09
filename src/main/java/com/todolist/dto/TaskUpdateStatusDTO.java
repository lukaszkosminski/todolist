package com.todolist.dto;

import com.todolist.model.StatusTask;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskUpdateStatusDTO {
    private long idTask;
    private StatusTask statusTask;
}
