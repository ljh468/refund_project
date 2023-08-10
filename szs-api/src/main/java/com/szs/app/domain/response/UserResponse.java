package com.szs.app.domain.response;

import com.szs.app.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private String id;

  private String name;

  private String birth;

  private boolean isActivated;

  private boolean isDeleted;

  private List<AnnualIncomeResponse> annualIncomes = new ArrayList<>();

  private Set<AuthorityResponse> authorities = new HashSet<>();

  public static UserResponse from(User user) {
    if (isNull(user)) {
      return null;
    }
    return UserResponse.builder()
                       .id(user.getId())
                       .name(user.getName())
                       .birth(user.getRegNoFront())
                       .isActivated(user.isActivated())
                       .isDeleted(user.isDeleted())
                       .annualIncomes(user.getAnnualIncomes().stream()
                                     .map(AnnualIncomeResponse::from).collect(Collectors.toList()))
                       .authorities(user.getAuthorities().stream()
                                   .map(authority -> AuthorityResponse.builder().authorityName(authority.getAuthorityName()).build())
                                   .collect(Collectors.toSet()))
                       .build();
  }
}
