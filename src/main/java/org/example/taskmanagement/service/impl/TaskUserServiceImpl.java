package org.example.taskmanagement.service.impl;

import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.entity.TaskUserRole;
import org.example.taskmanagement.domain.exception.UserNotFoundException;
import org.example.taskmanagement.domain.mapper.TaskUserMapper;
import org.example.taskmanagement.domain.repository.TaskUserRepository;
import org.example.taskmanagement.domain.request.TaskUserRequest;
import org.example.taskmanagement.domain.response.TaskUserResponse;
import org.example.taskmanagement.service.TaskUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskUserServiceImpl implements TaskUserService {

  private final TaskUserRepository taskUserRepository;

  private final TaskUserMapper taskUserMapper;

  private final PasswordEncoder passwordEncoder;

  @Override
  public TaskUserResponse save(TaskUserRequest taskUserRequest) {
    TaskUser taskUser = taskUserMapper.taskUserRequestToTaskUser(taskUserRequest);
    taskUser.setPassword(passwordEncoder.encode(taskUser.getPassword()));
    taskUser.setTaskUserRole(TaskUserRole.USER);
    TaskUser save = taskUserRepository.save(taskUser);

    return taskUserMapper.taskUserToTaskUserResponse(save);
  }

  @Override
  public TaskUser findByEmail(String email) {
    return taskUserRepository.findByEmail(email).orElseThrow(RuntimeException::new);
  }

  @Override
  public TaskUser findById(String id) {
    return taskUserRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    TaskUser byEmail = findByEmail(username);
    return new User(byEmail.getEmail(), byEmail.getPassword(),
        Collections.singleton(byEmail.getTaskUserRole()));
  }
}