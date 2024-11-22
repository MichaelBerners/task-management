package org.example.taskmanagement.service;

import java.util.List;
import org.example.taskmanagement.domain.entity.Task;
import org.example.taskmanagement.domain.request.TaskReadRequest;
import org.example.taskmanagement.domain.request.TaskRequest;
import org.example.taskmanagement.domain.response.TaskResponse;

public interface TaskService {

  /**
   * создание задачи
   */
  TaskResponse create(TaskRequest taskRequest);

  /**
   * просмотр конкретной задачи
   */
  Task readById(String id);

  /**
   * получить задачи конкретного автора
   */
  List<TaskResponse> readTaskByAuthor(TaskReadRequest taskReadRequest);

  /**
   * получить задачи конкретного исполнителя
   */
  List<TaskResponse> readTaskByExecutor(TaskReadRequest taskReadRequest);

  /**
   * удаление задачи
   */
  void delete(String id);

  /**
   * изменение статуса задачи
   */
  TaskResponse updateStatus(String id, String status);

  /**
   * изменение приоритета задачи
   */
  TaskResponse updatePriority(String id, String priority);

  /**
   * назначить исполнителя задачи
   */
  TaskResponse taskAssign(String taskId, String executorId);
}