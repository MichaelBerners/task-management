package org.example.taskmanagement.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
@Table(name = "task_users")
public class TaskUser {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String firstName;

  private String lastName;

  @Enumerated(value = EnumType.STRING)
  private TaskUserRole taskUserRole;

  private String email;

  private String password;
}