package org.example.taskmanagement.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum TaskUserRole implements GrantedAuthority {
  ADMIN, USER;

  @Override
  public String getAuthority() {
    return name();
  }
}