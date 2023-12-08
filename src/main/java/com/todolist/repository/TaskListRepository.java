package com.todolist.repository;

import com.todolist.model.Task;
import com.todolist.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
