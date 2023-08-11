package com.szs.app.domain.response;

import com.szs.app.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {

  private String id;

  private String name;

  private LocalDateTime createdAt;

  private Set<AuthorityResponse> authorities;

  public static SignUpResponse from(User user) {
    if (isNull(user)) {
      return null;
    }
    return SignUpResponse.builder()
                         .id(user.getId())
                         .name(user.getName())
                         .authorities(user.getAuthorities().stream()
                                          .map(authority -> AuthorityResponse.builder().authorityName(authority.getAuthorityName()).build())
                                          .collect(Collectors.toSet()))
                         .createdAt(user.getCreatedAt())
                         .build();
  }
}
