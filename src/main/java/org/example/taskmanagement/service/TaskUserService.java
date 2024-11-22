package org.example.taskmanagement.service;

import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.request.TaskUserRequest;
import org.example.taskmanagement.domain.response.TaskUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TaskUserService extends UserDetailsService{

  /**
   * создание пользователя
   */
  TaskUserResponse save(TaskUserRequest taskUserRequest);

  /**
   * поиск пользователя по email
   */
  TaskUser findByEmail(String email);

  /**
   * поиск пользователя по id
   */
  TaskUser findById(String id);
}
