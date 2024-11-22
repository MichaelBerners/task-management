package org.example.taskmanagement.domain.mapper;

import org.example.taskmanagement.domain.entity.TaskUser;
import org.example.taskmanagement.domain.request.TaskUserRequest;
import org.example.taskmanagement.domain.response.TaskUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskUserMapper {

  TaskUser taskUserRequestToTaskUser(TaskUserRequest taskUserRequest);
  TaskUserResponse taskUserToTaskUserResponse(TaskUser taskUser);

}
