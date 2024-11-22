package org.example.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.request.CommentRequest;
import org.example.taskmanagement.domain.response.CommentResponse;
import org.example.taskmanagement.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/create/{taskId}")
  @Operation(summary = "создание комментария к заданию")
  public ResponseEntity<CommentResponse> create(
      @PathVariable String taskId,
      @RequestBody CommentRequest commentRequest
  ) {
    return ResponseEntity.ok(commentService.create(taskId, commentRequest));
  }

  @GetMapping("/read")
  @Operation(summary = "найти все комментарии соответствующего задания")
  public ResponseEntity<List<CommentResponse>> readAllByTask(@RequestParam String taskId) {

    return ResponseEntity.ok(commentService.fiendCommentByTask(taskId));
  }
}