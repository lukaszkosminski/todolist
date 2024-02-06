//package com.todolist.dto.mapper;
//
//import com.todolist.dto.TaskCollectionIdDTO;
//import com.todolist.model.TaskCollection;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class TaskCollectionIdMapperTest {
//
//    @Test
//    @DisplayName("Test map TaskCollectionIdDTO too TaskCollection")
//    public void testMapTaskCollectionIdDTOToTaskCollection() {
//
//        TaskCollectionIdDTO taskListIdDTO = new TaskCollectionIdDTO();
//        taskListIdDTO.setName("Test Task Collection");
//        taskListIdDTO.setId(1L);
//
//        TaskCollection result = TaskCollectionIdMapper.mapToTaskList(taskListIdDTO);
//
//        assertEquals(taskListIdDTO.getName(), result.getName());
//        assertEquals(taskListIdDTO.getId(), result.getId());
//
//    }
//
//    @Test
//    @DisplayName("Test map TaskCollection to TaskCollectionIdDTO")
//    public void testMapTaskCollectionToTaskCollectionIdDTO() {
//
//        TaskCollection taskList = new TaskCollection();
//        taskList.setName("Test Task Collection");
//        taskList.setId(1L);
//
//        TaskCollectionIdDTO result = TaskCollectionIdMapper.mapToDTOWithId(taskList);
//
//        assertEquals(taskList.getName(), result.getName());
//        assertEquals(taskList.getId(), result.getId());
//
//    }
//
//}