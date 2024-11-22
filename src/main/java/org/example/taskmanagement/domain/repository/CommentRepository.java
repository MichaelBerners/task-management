package org.example.taskmanagement.domain.repository;

import java.util.List;
import java.util.UUID;
import org.example.taskmanagement.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

  @Query("SELECT c FROM Comment c JOIN c.task t WHERE t.id = :taskId")
  List<Comment> findAllByTaskId(UUID taskId);
}