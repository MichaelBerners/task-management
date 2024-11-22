package org.example.taskmanagement.domain.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {

  @Bean
  PostgreSQLContainer<?> postgresContainer() {

    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
  }
}