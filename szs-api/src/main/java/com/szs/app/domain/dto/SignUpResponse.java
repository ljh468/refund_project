package com.szs.app.domain.dto;

import com.szs.app.domain.entity.Authority;
import com.szs.app.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class SignUpResponse {

  private String id;

  private String name;

  private boolean isActivated;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Set<Authority> authorities;

  public SignUpResponse() {
  }

  public SignUpResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.isActivated = user.isActivated();
    this.isDeleted = user.isDeleted();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.authorities = user.getAuthorities();
  }
}
