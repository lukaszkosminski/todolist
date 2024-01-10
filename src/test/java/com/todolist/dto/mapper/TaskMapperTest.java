package com.todolist.dto.mapper;

import com.todolist.dto.TaskDTO;
import com.todolist.model.PriorityTask;
import com.todolist.model.StatusTask;
import com.todolist.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    @Test
    @DisplayName("Test map TaskDTO to Task")
    void testMapTaskDTOtoTask() {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStatusTask(StatusTask.IN_PROGRESS);
        taskDTO.setPriorityTask(PriorityTask.LOW);
        taskDTO.setDescription("test description");
        taskDTO.setTitle("test title");


        Task task = TaskMapper.mapToTask(taskDTO);

        assertEquals(taskDTO.getStatusTask(), task.getStatusTask());
        assertEquals(taskDTO.getPriorityTask(), task.getPriorityTask());
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());

    }

    @Test
    @DisplayName("Test map Task to TaskDTO")
    void testMapTaskToTaskDTO() {

        Task task = new Task();
        task.setDescription("test description");
        task.setPriorityTask(PriorityTask.LOW);
        task.setStatusTask(StatusTask.TODO);
        task.setTitle("test title");

        TaskDTO taskDTO = TaskMapper.mapToDTO(task);

        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getPriorityTask(), taskDTO.getPriorityTask());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getStatusTask(), taskDTO.getStatusTask());

    }

}