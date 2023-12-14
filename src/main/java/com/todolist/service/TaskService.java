package com.todolist.service;

import com.todolist.dto.TaskCollectionDTO;
import com.todolist.dto.TaskCollectionIdDTO;
import com.todolist.dto.TaskDTO;
import com.todolist.dto.TaskIdDTO;
import com.todolist.dto.mapper.TaskCollectionIdMapper;
import com.todolist.dto.mapper.TaskCollectionMapper;
import com.todolist.dto.mapper.TaskIdMapper;
import com.todolist.dto.mapper.TaskMapper;
import com.todolist.model.Task;
import com.todolist.model.TaskCollection;
import com.todolist.model.User;
import com.todolist.repository.TaskCollectionRepository;
import com.todolist.repository.TaskRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCollectionRepository taskCollectionRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository, TaskCollectionRepository taskCollectionRepository) {
        this.taskRepository = taskRepository;
        this.taskCollectionRepository = taskCollectionRepository;
    }


    public TaskDTO createTask(TaskDTO taskDTO, Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        TaskCollection taskCollection = findTasklist(idTaskCollection);
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskCollection(taskCollection);
        taskRepository.save(task);
        return taskDTO;
    }

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask, User user, Long idTaskCollection) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        task.setTitle(taskDTO.getTitle());
        task.setStatusTask(taskDTO.getStatusTask());
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        return TaskMapper.mapToDTO(task);
    }

    public void deleteTask(Long idTask, User user, Long idTaskCollection) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        taskRepository.delete(task);
    }

    public void deleteTasksByTaskList(Long idTaskCollection, User user) throws Exception {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }

        List<Task> tasksToDelete = taskRepository.findByTaskCollectionId(idTaskCollection);

        if (tasksToDelete.isEmpty()) {
            throw new NotFoundException("No tasks found for TaskList with id " + idTaskCollection);
        }

        taskRepository.deleteAll(tasksToDelete);
    }

    public TaskDTO getTask(Long idTask, Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.mapToDTO(task);
    }

    public List<TaskIdDTO> getTasks(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        List<Task> taskId = taskRepository.findByTaskCollectionId(idTaskCollection);
        ArrayList<TaskIdDTO> taskIdDTO = new ArrayList<>();

        for (Task task : taskId) {
            taskIdDTO.add(TaskIdMapper.mapToIdDTO(task));
        }
        return taskIdDTO;
    }

    public TaskCollectionDTO saveDefaultEmptyList(User user) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName("default");
        taskCollection.setUser(user);
        taskCollectionRepository.save(taskCollection);
        return TaskCollectionMapper.mapToDTO(taskCollection);
    }

    public TaskCollection findTasklist(Long idTaskCollection) throws NotFoundException {
        return taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> new NotFoundException("TaskList not found"));
    }

    public boolean userContainsTaskListId(User user, Long idTaskCollection) {
        List<TaskCollection> taskListByUser = taskCollectionRepository.findByUser(user);
        for (TaskCollection taskCollection : taskListByUser) {
            if (taskCollection.getId().equals(idTaskCollection)) {
                return true;
            }
        }
        return false;
    }

    public TaskCollectionIdDTO createTaskCollection(TaskCollectionDTO taskCollectionDTO, User user) {
        TaskCollection taskCollection = TaskCollectionMapper.mapToTaskList(taskCollectionDTO);
        taskCollection.setUser(user);
        taskCollectionRepository.save(taskCollection);
        return TaskCollectionIdMapper.mapToDTOWithId(taskCollection);
    }

    public TaskCollectionDTO editTaskCollection(TaskCollectionDTO taskCollectionDTO, User user, Long idTaskCollection) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> new NotFoundException("TaskList not found"));
        taskCollection.setName(taskCollectionDTO.getName());
        taskCollectionRepository.save(taskCollection);
        return TaskCollectionMapper.mapToDTO(taskCollection);
    }

    public void deleteTaskCollection(User user, Long idTaskCollection) throws Exception {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        deleteTasksByTaskList(idTaskCollection, user);
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> new NotFoundException("TaskList with id " + idTaskCollection + " not found"));
        taskCollectionRepository.delete(taskCollection);
    }

    public TaskCollectionIdDTO getTaskCollection(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskListId(user, idTaskCollection)) {
            throw new NotFoundException("TaskList with id " + idTaskCollection + " not found for the user");
        }
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> new NotFoundException("TaskList with id " + idTaskCollection + " not found"));
        return TaskCollectionIdMapper.mapToDTOWithId(taskCollection);
    }

    public List<TaskCollectionIdDTO> getTaskCollections(User user) {
        List<TaskCollection> taskListsByUser = taskCollectionRepository.findByUser(user);
        ArrayList<TaskCollectionIdDTO> taskListsIdDTO = new ArrayList<>();

        for (TaskCollection taskCollection : taskListsByUser) {
            taskListsIdDTO.add(TaskCollectionIdMapper.mapToDTOWithId(taskCollection));
        }

        return taskListsIdDTO;

    }


}
