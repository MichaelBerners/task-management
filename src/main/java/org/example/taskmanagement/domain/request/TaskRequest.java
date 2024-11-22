package org.example.taskmanagement.domain.request;

public record TaskRequest(
    String heading,
    String description,
    String priority
) {}
