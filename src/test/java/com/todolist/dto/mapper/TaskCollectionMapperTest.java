package com.todolist.dto.mapper;

import com.todolist.dto.TaskCollectionCreateDTO;
import com.todolist.dto.TaskCollectionDTO;
import com.todolist.model.TaskCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskCollectionMapperTest {

    @Test
    @DisplayName("Test map TaskCollectionDTO to TaskCollection")
    public void testMapTaskCollectionIdDTOToTaskCollection() {

        TaskCollectionDTO taskListDTO = new TaskCollectionDTO();
        taskListDTO.setName("Test Task Collection");

        TaskCollection result = TaskCollectionMapper.taskCollectionDTOMapToTaskCollection(taskListDTO);

        assertEquals(taskListDTO.getName(), result.getName());

    }

    @Test
    @DisplayName("Test map TaskCollection to TaskCollectionDTO")
    public void testMapTaskCollectionToTaskCollectionDTO() {

        TaskCollection taskList = new TaskCollection();
        taskList.setName("Test Task Collection");

        TaskCollectionDTO result = TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskList);

        assertEquals(taskList.getName(), result.getName());

    }

    @Test
    @DisplayName("Test map TaskCollectionCreateDTO to TaskCollection")
    public void testTaskCollectionCreateDTOMapToTaskCollection() {
        // Arrange
        TaskCollectionCreateDTO taskCollectionCreateDTO = new TaskCollectionCreateDTO();
        taskCollectionCreateDTO.setName("Sample Collection");

        // Act
        TaskCollection resultTaskCollection = TaskCollectionMapper.taskCollectionCreateDTOMapToTaskCollection(taskCollectionCreateDTO);

        // Assert
        assertNotNull(resultTaskCollection);
        assertEquals(taskCollectionCreateDTO.getName(), resultTaskCollection.getName());
    }
}