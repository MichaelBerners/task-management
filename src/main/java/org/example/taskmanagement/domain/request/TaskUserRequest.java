package org.example.taskmanagement.domain.request;

public record TaskUserRequest(
    String firstName,
    String lastName,
    String taskUserRole,
    String email,
    String password
) {}
