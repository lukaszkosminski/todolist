package com.todolist.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTask;
    private PriorityTask priorityTask;
    private String description;
    private LocalDateTime dateTime;
    private String title;
    private StatusTask statusTask;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
