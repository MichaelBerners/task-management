package org.example.taskmanagement.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.entity.Priority;
import org.example.taskmanagement.domain.entity.Task;
import org.example.taskmanagement.domain.entity.TaskStatus;
import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.entity.TaskUserRole;
import org.example.taskmanagement.domain.exception.EditingException;
import org.example.taskmanagement.domain.exception.TaskNotFoundException;
import org.example.taskmanagement.domain.mapper.TaskMapper;
import org.example.taskmanagement.domain.repository.TaskRepository;
import org.example.taskmanagement.domain.request.TaskReadRequest;
import org.example.taskmanagement.domain.request.TaskRequest;
import org.example.taskmanagement.domain.response.TaskResponse;
import org.example.taskmanagement.service.TaskService;
import org.example.taskmanagement.service.TaskUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskUserService taskUserService;

  private final TaskRepository taskRepository;

  private final TaskMapper taskMapper;

  @Override
  public TaskResponse create(TaskRequest taskRequest) {
    String email = getUserDetails().getUsername();
    TaskUser author = taskUserService.findByEmail(email);
    Task task = taskMapper.taskRequestToTask(taskRequest);
    task.setAuthor(author);
    task.setTaskStatus(TaskStatus.IN_WAITING);
    Task save = taskRepository.save(task);

    return taskMapper.taskToTaskResponse(save);
  }

  @Override
  public Task readById(String id) {
    return taskRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new TaskNotFoundException("Задание не найдено"));
  }

  @Override
  public List<TaskResponse> readTaskByAuthor(TaskReadRequest taskReadRequest) {
    UUID authorId = UUID.fromString(taskReadRequest.userId());
    int page = taskReadRequest.page() - 1;
    int size = taskReadRequest.size();
    PageRequest pageRequest = PageRequest.of(page - 1, size);

    return taskRepository.findAllByAuthor(authorId, pageRequest).stream()
        .map(taskMapper::taskToTaskResponse).toList();
  }

  @Override
  public List<TaskResponse> readTaskByExecutor(TaskReadRequest taskReadRequest) {
    UUID executorId = UUID.fromString(taskReadRequest.userId());
    int page = taskReadRequest.page() - 1;
    int size = taskReadRequest.size();
    PageRequest pageRequest = PageRequest.of(page - 1, size);

    return taskRepository.findAllByExecutor(executorId, pageRequest).stream()
        .map(taskMapper::taskToTaskResponse).toList();
  }

  @Override
  public void delete(String id) {
    Task task = readById(id);
    taskRepository.delete(task);
  }

  @Override
  public TaskResponse updateStatus(String id, String status) {
    UserDetails userDetails = getUserDetails();
    Task task = readById(id);
    if (!userDetails.getAuthorities().contains(TaskUserRole.ADMIN)
        || !userDetails.getUsername().equals(task.getExecutor().getEmail())) {
      throw new EditingException("Редактирование запрещено");
    }
    task.setTaskStatus(TaskStatus.valueOf(status));
    Task save = taskRepository.save(task);

    return taskMapper.taskToTaskResponse(save);
  }

  @Override
  @Transactional
  public TaskResponse updatePriority(String id, String priority) {
    Task task = readById(id);
    task.setPriority(Priority.valueOf(priority));

    return taskMapper.taskToTaskResponse(taskRepository.save(task));
  }

  @Override
  public TaskResponse taskAssign(String taskId, String executorId) {
    TaskUser executor = taskUserService.findById(executorId);
    Task task = readById(taskId);
    task.setExecutor(executor);

    return taskMapper.taskToTaskResponse(taskRepository.save(task));
  }

  private UserDetails getUserDetails() {
    return (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
  }
}