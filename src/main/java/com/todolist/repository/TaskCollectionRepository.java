package com.todolist.repository;

import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCollectionRepository extends JpaRepository<TaskCollection, Long> {
    List<TaskCollection> findByUser(User user);
    Long findIdByName(User user);

    Optional<TaskCollection> findByUserAndId(User user, Long idTaskCollection);

}
