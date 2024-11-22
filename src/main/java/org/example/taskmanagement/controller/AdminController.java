package org.example.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.request.CommentRequest;
import org.example.taskmanagement.domain.request.TaskReadRequest;
import org.example.taskmanagement.domain.request.TaskRequest;
import org.example.taskmanagement.domain.response.CommentResponse;
import org.example.taskmanagement.domain.response.TaskResponse;
import org.example.taskmanagement.service.CommentService;
import org.example.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final TaskService taskService;

  private final CommentService commentService;

  @PostMapping("/create")
  @Operation(summary = "создание задания")
  public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
    return ResponseEntity.ok(taskService.create(taskRequest));
  }

  @GetMapping("/task/read/author")
  @Operation(summary = "поиск всех заданий по автору (с пагинацией)")
  public ResponseEntity<List<TaskResponse>> readTaskByAuthor(
      @RequestBody TaskReadRequest taskReadRequest) {
    return ResponseEntity.ok(taskService.readTaskByAuthor(taskReadRequest));
  }

  @GetMapping("/task/read/executor")
  @Operation(summary = "поиск всех заданий по исполнителю (с пагинацией)")
  public ResponseEntity<List<TaskResponse>> readTaskByExecutor(
      @RequestBody TaskReadRequest taskReadRequest) {
    return ResponseEntity.ok(taskService.readTaskByExecutor(taskReadRequest));
  }

  @PatchMapping("/task/update/priority")
  @Operation(summary = "изменение приоритета задания")
  public ResponseEntity<TaskResponse> updateTaskPriority(@RequestParam String taskId,
      @RequestParam String priority) {

    return ResponseEntity.ok(taskService.updatePriority(taskId, priority));
  }

  @PatchMapping("/task/update/status")
  @Operation(summary = "изменение статуса задания")
  public ResponseEntity<TaskResponse> updateTaskStatus(@RequestParam String taskId,
      @RequestParam String status) {
    return ResponseEntity.ok(taskService.updateStatus(taskId, status));
  }

  @PatchMapping("/task/update/assign")
  @Operation(summary = "изменение исполнителя задания")
  public ResponseEntity<TaskResponse> updateTaskExecutor(@RequestParam String taskId,
      @RequestParam String executorId) {
    return ResponseEntity.ok(taskService.taskAssign(taskId, executorId));
  }

  @PostMapping("/task/update/add-comment")
  @Operation(summary = "добавление комментария к заданию")
  public ResponseEntity<CommentResponse> addCommentToTask(@RequestParam String taskId,
      @RequestBody CommentRequest commentRequest) {
    return ResponseEntity.ok(commentService.create(taskId, commentRequest));
  }

  @DeleteMapping("/task/delete")
  @Operation(summary = "удаление задания по id")
  public ResponseEntity deleteTask(@RequestParam String taskId) {
    taskService.delete(taskId);
    return ResponseEntity.ok("Success!");
  }
}