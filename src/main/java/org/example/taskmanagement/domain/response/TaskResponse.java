package org.example.taskmanagement.domain.response;

public record TaskResponse(
    String heading,
    String description,
    String taskStatus,
    String priority,
    String authorEmail,
    String executorEmail
) {}
