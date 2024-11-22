package org.example.taskmanagement.domain.request;

public record TaskReadRequest(
    String userId,
    Integer page,
    Integer size
) {}
