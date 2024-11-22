package org.example.taskmanagement.domain.mapper;

import org.example.taskmanagement.domain.entity.Comment;
import org.example.taskmanagement.domain.request.CommentRequest;
import org.example.taskmanagement.domain.response.CommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  Comment commentRequestToComment(CommentRequest commentRequest);

  CommentResponse commentToCommentResponse(Comment comment);
}
