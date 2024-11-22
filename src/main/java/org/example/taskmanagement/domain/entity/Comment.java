package org.example.taskmanagement.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String comment;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;
}