package org.example.taskmanagement.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.entity.Comment;
import org.example.taskmanagement.domain.entity.Task;
import org.example.taskmanagement.domain.entity.TaskUserRole;
import org.example.taskmanagement.domain.exception.EditingException;
import org.example.taskmanagement.domain.mapper.CommentMapper;
import org.example.taskmanagement.domain.repository.CommentRepository;
import org.example.taskmanagement.domain.request.CommentRequest;
import org.example.taskmanagement.domain.response.CommentResponse;
import org.example.taskmanagement.service.CommentService;
import org.example.taskmanagement.service.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final TaskService taskService;

  private final CommentRepository commentRepository;

  private final CommentMapper commentMapper;

  @Override
  @Transactional
  public CommentResponse create(String taskId, CommentRequest commentRequest) {
    Task task = taskService.readById(taskId);
    UserDetails userDetails = getUserDetails();
    if(!userDetails.getAuthorities().contains(TaskUserRole.ADMIN)
        || !userDetails.getUsername().equals(task.getExecutor().getEmail())) {
      throw new EditingException("Редактирование запрещено");
    }
    Comment comment = commentMapper.commentRequestToComment(commentRequest);
    comment.setTask(task);
    commentRepository.save(comment);

    return commentMapper.commentToCommentResponse(comment);
  }

  @Override
  public List<CommentResponse> fiendCommentByTask(String id) {
    Task task = taskService.readById(id);
    UserDetails userDetails = getUserDetails();
    if(!userDetails.getUsername().equals(task.getExecutor().getEmail())) {
      throw new EditingException("Редактирование запрещено");
    }
    List<Comment> allByTaskId = commentRepository.findAllByTaskId(task.getId());

    return allByTaskId.stream().map(commentMapper::commentToCommentResponse).toList();
  }

  private UserDetails getUserDetails() {
    return (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
  }
}