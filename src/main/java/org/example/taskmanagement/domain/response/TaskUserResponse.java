package org.example.taskmanagement.domain.response;

public record TaskUserResponse(
    String firstName,
    String lastName,
    String taskUserRole,
    String email,
    String password
) {}
