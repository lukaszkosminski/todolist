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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCollectionRepository taskCollectionRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository, TaskCollectionRepository taskCollectionRepository) {
        this.taskRepository = taskRepository;
        this.taskCollectionRepository = taskCollectionRepository;
    }


    public TaskDTO createTask(TaskDTO taskDTO, Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        TaskCollection taskCollection = findTaskCollection(idTaskCollection);
        Task task = TaskMapper.mapToTask(taskDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskCollection(taskCollection);
        taskRepository.save(task);
        log.info("Task created successfully. Task ID: {}, User ID: {}, Task Colletion ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        return taskDTO;
    }

    public TaskDTO editTask(TaskDTO taskDTO, Long idTask, User user, Long idTaskCollection) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        task.setTitle(taskDTO.getTitle());
        task.setStatusTask(taskDTO.getStatusTask());
        task.setPriorityTask(taskDTO.getPriorityTask());
        task.setDescription(taskDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        log.info("Task edited successfully. Task ID: {}, User ID: {}, Task List ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        return TaskMapper.mapToDTO(task);
    }

    public void deleteTask(Long idTask, User user, Long idTaskCollection) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        log.info("Task deleted successfully. Task ID: {}, User ID: {}, Task List ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        taskRepository.delete(task);
    }

    public void deleteTasksByTaskCollection(Long idTaskCollection, User user) throws Exception {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }

        List<Task> tasksToDelete = taskRepository.findByTaskCollectionId(idTaskCollection);

        if (tasksToDelete.isEmpty()) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        log.info("All tasks deleted successfully from the task collection. User ID: {}, Collection List ID: {}",
                user.getUserId(), idTaskCollection);
        taskRepository.deleteAll(tasksToDelete);
    }

    public TaskDTO getTask(Long idTask, Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        log.info("Task found with ID: {}", idTask);
        return TaskMapper.mapToDTO(task);
    }

    public List<TaskIdDTO> getTasks(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        List<Task> taskId = taskRepository.findByTaskCollectionId(idTaskCollection);
        ArrayList<TaskIdDTO> taskIdDTO = new ArrayList<>();

        for (Task task : taskId) {
            taskIdDTO.add(TaskIdMapper.mapToIdDTO(task));
        }
        log.info("Task List found with user ID: {}", user.getUserId());
        return taskIdDTO;
    }

    public TaskCollectionDTO saveDefaultTaskCollection(User user) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName("default");
        taskCollection.setUser(user);
        taskCollectionRepository.save(taskCollection);
        log.info("Default empty Task Collection created successfully for user {}", user.getUserId());
        return TaskCollectionMapper.mapToDTO(taskCollection);
    }

    public TaskCollection findTaskCollection(Long idTaskCollection) throws NotFoundException {
        return taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> {
            log.error("Task Collection not found with ID: {}", idTaskCollection);
            return new NotFoundException("Task Collection not found");
        });

    }

    public boolean userContainsTaskCollectionId(User user, Long idTaskCollection) {
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
        log.info("TaskCollection created successfully for user {} with ID: {}", user.getUserId(), taskCollection.getId());
        return TaskCollectionIdMapper.mapToDTOWithId(taskCollection);
    }

    public TaskCollectionDTO editTaskCollection(TaskCollectionDTO taskCollectionDTO, User user, Long idTaskCollection) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection)
                .orElseThrow(() -> {
                    log.error("Task Collection with id {} not found for the user {}", idTaskCollection, user.getUserId());
                    return new NotFoundException("Task Collection not found");
                });
        taskCollection.setName(taskCollectionDTO.getName());
        taskCollectionRepository.save(taskCollection);
        log.info("Task Collection edited successfully. Task Collection ID: {}, User ID: {}", idTaskCollection, user.getUserId());
        return TaskCollectionMapper.mapToDTO(taskCollection);
    }

    public void deleteTaskCollection(User user, Long idTaskCollection) throws Exception {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        deleteTasksByTaskCollection(idTaskCollection, user);
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection)
                .orElseThrow(() -> {
                    log.error("Task Collection with id {} not found for the user {}", idTaskCollection, user.getUserId());
                    return new NotFoundException("Task Collection not found");
                });
        log.info("Task Collection deleted successfully. TaskList ID: {}, User ID: {}", idTaskCollection, user.getUserId());
        taskCollectionRepository.delete(taskCollection);
    }

    public TaskCollectionIdDTO getTaskCollection(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection)
                .orElseThrow(() -> {
                    log.error("Task Collection with id {} not found for the user {}", idTaskCollection, user.getUserId());
                    return new NotFoundException("Task Collection not found");
                });
        log.info("Task Collection retrieved successfully. TaskList ID: {}, User ID: {}", idTaskCollection, user.getUserId());
        return TaskCollectionIdMapper.mapToDTOWithId(taskCollection);
    }

    public List<TaskCollectionIdDTO> getTaskCollections(User user) {

        List<TaskCollection> taskCollections = taskCollectionRepository.findByUser(user);
        ArrayList<TaskCollectionIdDTO> taskListsIdDTO = new ArrayList<>();

        for (TaskCollection taskCollection : taskCollections) {
            taskListsIdDTO.add(TaskCollectionIdMapper.mapToDTOWithId(taskCollection));
        }

        log.info("TaskCollections retrieved successfully for User ID: {}", user.getUserId());
        return taskListsIdDTO;

    }


}
