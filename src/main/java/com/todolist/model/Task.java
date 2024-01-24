package com.todolist.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "task")
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
    @JoinColumn(name = "task_collection_id")
    private TaskCollection taskCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(idTask, task.idTask) && priorityTask == task.priorityTask && Objects.equals(description, task.description) && Objects.equals(dateTime, task.dateTime) && Objects.equals(title, task.title) && statusTask == task.statusTask && Objects.equals(taskCollection, task.taskCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, priorityTask, description, dateTime, title, statusTask, taskCollection);
    }
}
