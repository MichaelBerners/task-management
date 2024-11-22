package org.example.taskmanagement.domain.repository;

import java.util.UUID;
import org.example.taskmanagement.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, UUID> {

  @Query("SELECT t FROM Task t JOIN FETCH t.author a WHERE a.id = :authorId")
  Page<Task> findAllByAuthor(UUID authorId, Pageable pageable);

  @Query("SELECT t FROM Task t JOIN FETCH t.executor e WHERE e.id = :executorId")
  Page<Task> findAllByExecutor(UUID executorId, Pageable pageable);
}