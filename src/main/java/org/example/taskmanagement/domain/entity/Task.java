package org.example.taskmanagement.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String heading;

  private String description;

  @Enumerated(value = EnumType.STRING)
  private TaskStatus taskStatus;

  @Enumerated(value = EnumType.STRING)
  private Priority priority;

  @OneToMany(mappedBy = "task")
  private List<Comment> comment;

  @ManyToOne
  @JoinColumn(name = "author_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private TaskUser author;

  @ManyToOne
  @JoinColumn(name = "executor_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private TaskUser executor;
}