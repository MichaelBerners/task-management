package org.example.taskmanagement.domain.mapper;

import java.util.List;
import org.example.taskmanagement.domain.entity.Task;
import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.request.TaskRequest;
import org.example.taskmanagement.domain.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  @Mapping(target = "authorEmail", source = "task.author.email")
  @Mapping(target = "executorEmail", source = "task.executor.email")
  TaskResponse taskToTaskResponse(Task task);

  Task taskRequestToTask(TaskRequest taskRequest);
}