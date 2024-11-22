package org.example.taskmanagement.service;

import java.util.List;
import org.example.taskmanagement.domain.request.CommentRequest;
import org.example.taskmanagement.domain.response.CommentResponse;

public interface CommentService {

  /**
   * добавить комментарий
   */
  CommentResponse create(String taskId, CommentRequest commentRequest);

  /**
   * показать комментарии к задаче
   */
  List<CommentResponse> fiendCommentByTask(String id);
}