package com.todolist.dto.mapper;

import com.todolist.dto.TaskIdDTO;
import com.todolist.model.PriorityTask;
import com.todolist.model.StatusTask;
import com.todolist.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskIdMapperTest {
    @Test
    @DisplayName("Test map TaskIdDTO to Task")
    public void testMapTaskIdDTOtoTask() {

        TaskIdDTO taskIdDTO = new TaskIdDTO();
        taskIdDTO.setPriorityTask(PriorityTask.LOW);
        taskIdDTO.setStatusTask(StatusTask.IN_PROGRESS);
        taskIdDTO.setDescription("test desciption");
        taskIdDTO.setIdTask(1L);
        taskIdDTO.setTitle("test title");

        Task task = TaskIdMapper.mapToTask(taskIdDTO);

        assertEquals(taskIdDTO.getPriorityTask(), task.getPriorityTask());
        assertEquals(taskIdDTO.getStatusTask(), task.getStatusTask());
        assertEquals(taskIdDTO.getDescription(), task.getDescription());
        assertEquals(taskIdDTO.getIdTask(), task.getIdTask());
        assertEquals(taskIdDTO.getTitle(), task.getTitle());

    }

    @Test
    @DisplayName("Test map Task to TaskIdDTO")
    public void testMapTasktoTaskIdDTO() {

        Task task = new Task();
        task.setPriorityTask(PriorityTask.LOW);
        task.setStatusTask(StatusTask.IN_PROGRESS);
        task.setDescription("test desciption");
        task.setIdTask(1L);
        task.setTitle("test title");

        TaskIdDTO taskIdDTO = TaskIdMapper.mapToIdDTO(task);

        assertEquals(task.getPriorityTask(),taskIdDTO.getPriorityTask());
        assertEquals(task.getStatusTask(),taskIdDTO.getStatusTask());
        assertEquals(task.getDescription(), taskIdDTO.getDescription());
        assertEquals(task.getIdTask(), taskIdDTO.getIdTask());
        assertEquals(task.getTitle(), taskIdDTO.getTitle());

    }

}