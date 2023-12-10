package com.todolist.repository;

import com.todolist.model.Task;
import com.todolist.model.TaskList;
import com.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdTask(Long idTask);

    List<Task> findByTaskListId(Long idTaskList);

}
