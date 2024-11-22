package org.example.taskmanagement.domain.repository;

import java.util.Optional;
import java.util.UUID;
import org.example.taskmanagement.domain.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {

  Optional<TaskUser> findByEmail(String email);
}
