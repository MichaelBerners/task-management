package org.example.taskmanagement.domain.repository;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.example.taskmanagement.domain.configuration.TestContainerConfig;
import org.example.taskmanagement.domain.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clear.sql", "file:src/test/resources/db/insert.sql"})
class TaskRepositoryTest {

  @Autowired
  private TaskRepository taskRepository;

  @Test
  void findAllByAuthor() {
    UUID authorId = UUID.fromString("022460aa-5f6a-4b0a-8c53-b336696e3a69");
    PageRequest pageRequest = PageRequest.of(0, 5);

    Page<Task> result = taskRepository.findAllByAuthor(authorId, pageRequest);

    Assertions.assertThat(result).hasSize(3);
  }
}