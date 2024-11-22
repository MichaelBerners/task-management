package org.example.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.response.TaskResponse;
import org.example.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @PatchMapping("/update/status")
  @Operation(summary = "изменение задания определенного пользователя")
  public ResponseEntity<TaskResponse> update(@RequestParam String taskId,
      @RequestParam String status) {
    return ResponseEntity.ok(taskService.updateStatus(taskId, status));
  }
}