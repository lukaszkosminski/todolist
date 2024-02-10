package com.todolist.service;

import com.todolist.dto.*;
import com.todolist.dto.mapper.TaskCollectionMapper;
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
import java.util.Optional;


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


    public TaskDTO createTask(TaskCreateDTO taskCreateDTO, Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        TaskCollection taskCollection = findTaskCollection(idTaskCollection);
        Task task = TaskMapper.taskCreateDTOMapToTask(taskCreateDTO);
        task.setDateTime(LocalDateTime.now());
        task.setTaskCollection(taskCollection);
        taskRepository.save(task);
        log.info("Task created successfully. Task ID: {}, User ID: {}, Task Colletion ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        return TaskMapper.taskMapToTaskDTO(task);
    }

    public TaskDTO editTask(TaskEditDTO taskEditDTO, Long idTask, User user, Long idTaskCollection) throws Exception {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }

        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        task.setTitle(taskEditDTO.getTitle());
        task.setStatusTask(taskEditDTO.getStatusTask());
        task.setPriorityTask(taskEditDTO.getPriorityTask());
        task.setDescription(taskEditDTO.getDescription());
        task.setDateTime(LocalDateTime.now());
        taskRepository.save(task);
        log.info("Task edited successfully. Task ID: {}, User ID: {}, Task List ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        return TaskMapper.taskMapToTaskDTO(task);
    }

    public void deleteTask(Long idTask, User user, Long idTaskCollection) throws Exception {
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        log.info("Task deleted successfully. Task ID: {}, User ID: {}, Task List ID: {}",
                task.getIdTask(), user.getUserId(), idTaskCollection);
        taskRepository.delete(task);
    }

    public void deleteTasksByTaskCollection(Long idTaskCollection, User user) throws Exception {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
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
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        Task task = taskRepository.findByIdTask(idTask).orElseThrow(() -> {
            log.error("Task not found with ID: {}", idTask);
            return new NotFoundException("Task not found");
        });
        log.info("Task found with ID: {}", idTask);
        return TaskMapper.taskMapToTaskDTO(task);
    }

    public List<TaskDTO> getTasks(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        List<Task> taskList = taskRepository.findByTaskCollectionId(idTaskCollection);
        ArrayList<TaskDTO> taskDTO = new ArrayList<>();

        for (Task task : taskList) {
            taskDTO.add(TaskMapper.taskMapToTaskDTO(task));
        }
        log.info("Task List found with user ID: {}", user.getUserId());
        return taskDTO;
    }

    public TaskCollectionDTO createDefaultTaskCollection(User user) {
        TaskCollection taskCollection = new TaskCollection();
        taskCollection.setName("default");
        taskCollection.setUser(user);
        taskCollectionRepository.save(taskCollection);
        log.info("Default empty Task Collection created successfully for user {}", user.getUserId());
        return TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskCollection);
    }

    public TaskCollection findTaskCollection(Long idTaskCollection) throws NotFoundException {
        return taskCollectionRepository.findById(idTaskCollection).orElseThrow(() -> {
            log.error("Task Collection not found with ID: {}", idTaskCollection);
            return new NotFoundException("Task Collection not found");
        });

    }

    public boolean userContainsTaskCollectionId(User user, Long idTaskCollection) {
        boolean isPresent = taskCollectionRepository.findByUserAndId(user, idTaskCollection).isPresent();
        if (!isPresent) {
            log.error("Task Collection with id " + idTaskCollection + " not found for the user with id " + user.getUserId());
        }
        log.info("Task Collection with id " + idTaskCollection + " found for the user with id " + user.getUserId());
        return isPresent;
    }

    public TaskCollectionDTO createTaskCollection(TaskCollectionCreateDTO taskCollectionCreateDTO, User user) {
        TaskCollection taskCollection = TaskCollectionMapper.taskCollectionCreateDTOMapToTaskCollection(taskCollectionCreateDTO);
        taskCollection.setUser(user);
        taskCollectionRepository.save(taskCollection);
        log.info("TaskCollection created successfully for user {} with ID: {}", user.getUserId(), taskCollection.getId());
        return TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskCollection);
    }

    public TaskCollectionDTO editTaskCollection(TaskCollectionEditDTO taskCollectionEditDTO, User user, Long idTaskCollection) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }

        Optional<TaskCollection> optionalTaskCollection = taskCollectionRepository.findById(idTaskCollection);

        if (optionalTaskCollection.isPresent()) {
            TaskCollection taskCollection = optionalTaskCollection.get();
            taskCollection.setName(taskCollectionEditDTO.getName());
            taskCollectionRepository.save(taskCollection);
            log.info("Task Collection edited successfully. Task Collection ID: {}, User ID: {}", idTaskCollection, user.getUserId());
            return TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskCollection);
        } else {
            log.error("Task Collection with id {} not found for the user {}", idTaskCollection, user.getUserId());
            throw new NotFoundException("Task Collection not found");
        }
    }

    public void deleteTaskCollection(User user, Long idTaskCollection) throws Exception {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
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

    public TaskCollectionDTO getTaskCollection(Long idTaskCollection, User user) throws NotFoundException {
        if (!userContainsTaskCollectionId(user, idTaskCollection)) {
            throw new NotFoundException("Task Collection with id " + idTaskCollection + " not found for the user with id" + user.getUserId());
        }
        TaskCollection taskCollection = taskCollectionRepository.findById(idTaskCollection)
                .orElseThrow(() -> {
                    log.error("Task Collection with id {} not found for the user {}", idTaskCollection, user.getUserId());
                    return new NotFoundException("Task Collection not found");
                });
        log.info("Task Collection retrieved successfully. TaskList ID: {}, User ID: {}", idTaskCollection, user.getUserId());
        return TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskCollection);
    }

    public List<TaskCollectionDTO> getTaskCollections(User user) {

        List<TaskCollection> taskCollections = taskCollectionRepository.findByUser(user);
        ArrayList<TaskCollectionDTO> taskListsDTO = new ArrayList<>();

        for (TaskCollection taskCollection : taskCollections) {
            taskListsDTO.add(TaskCollectionMapper.taskCollectionMapToTaskCollectionDTO(taskCollection));
        }

        log.info("TaskCollections retrieved successfully for User ID: {}", user.getUserId());
        return taskListsDTO;

    }

}
