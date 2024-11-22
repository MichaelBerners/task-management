package org.example.taskmanagement.service.impl;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.example.taskmanagement.domain.entity.Task;
import org.example.taskmanagement.domain.entity.TaskStatus;
import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.exception.TaskNotFoundException;
import org.example.taskmanagement.domain.mapper.TaskMapper;
import org.example.taskmanagement.domain.repository.TaskRepository;
import org.example.taskmanagement.domain.request.TaskRequest;
import org.example.taskmanagement.domain.response.TaskResponse;
import org.example.taskmanagement.service.TaskUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private TaskUserService taskUserService;

  @Mock
  private TaskMapper taskMapper;

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @Mock
  private UserDetails userDetails;

  @InjectMocks
  private TaskServiceImpl taskService;

  @Captor
  private ArgumentCaptor<Task> taskArgumentCaptor;

  @Test
  void testCreate_shouldReturnTaskResponse() {
    String email = "test@email.ru";
    TaskUser author = new TaskUser();
    author.setEmail(email);
    TaskRequest taskRequest = new TaskRequest(
        "testHeading",
        "testDescription",
        "testPriority"
    );
    Task task = new Task();
    TaskResponse taskResponse = new TaskResponse(
        "test",
        "test",
        "test",
        "test",
        "test",
        "test");

      when(userDetails.getUsername()).thenReturn(email);
      when(authentication.getPrincipal()).thenReturn(userDetails);
      when(securityContext.getAuthentication()).thenReturn(authentication);
      SecurityContextHolder.setContext(securityContext);
      when(taskUserService.findByEmail(email)).thenReturn(author);
      when(taskMapper.taskRequestToTask(taskRequest)).thenReturn(task);
      when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
      when(taskMapper.taskToTaskResponse(Mockito.any())).thenReturn(taskResponse);
      TaskResponse result = taskService.create(taskRequest);

      Assertions.assertThat(result).isEqualTo(taskResponse);
      Assertions.assertThat(taskArgumentCaptor.getValue())
          .matches(el -> el.getTaskStatus().equals(TaskStatus.IN_WAITING))
          .matches(el -> el.getAuthor().getEmail().equals(email));
  }

  @Test
  void testReadById_shouldReturnTask_whenTaskExist() {
    UUID id = UUID.fromString("d32ba584-00d6-40e0-b273-a3a7079302ef");
    Task task = new Task();
    task.setId(UUID.fromString("d32ba584-00d6-40e0-b273-a3a7079302ef"));

    when(taskRepository.findById(id)).thenReturn(Optional.of(task));
    Task result = taskService.readById("d32ba584-00d6-40e0-b273-a3a7079302ef");

    Assertions.assertThat(result).isEqualTo(task);
  }

  @Test
  void testReadById_shouldReturnException_whenTaskIsNotExist() {
    UUID id = UUID.fromString("d32ba584-00d6-40e0-b273-a3a7079302ef");

    when(taskRepository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThatThrownBy(
        () -> taskService.readById("d32ba584-00d6-40e0-b273-a3a7079302ef")).isInstanceOf(
        TaskNotFoundException.class).hasMessage("Задание не найдено");
  }
}