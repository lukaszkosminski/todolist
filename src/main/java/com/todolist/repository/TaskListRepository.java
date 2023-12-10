package com.todolist.repository;

import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByUser(User user);
}
