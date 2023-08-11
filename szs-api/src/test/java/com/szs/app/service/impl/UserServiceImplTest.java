package com.szs.app.service.impl;

import com.szs.app.exception.UserNotFoundException;
import com.szs.app.domain.entity.Authority;
import com.szs.app.domain.entity.User;
import com.szs.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  String mockUserId = "szsUserId";

  String mockPassword = "szsPassword";

  String mockName = "szsName";

  private final Authority mockAuthority = Authority.builder().authorityName("ROLE_USER").build();

  private User mockUser = User.builder()
                                     .id(mockUserId)
                                     .password(mockPassword)
                                     .name(mockName)
                                     .authorities(Set.of(mockAuthority))
                                     .isActivated(true)
                                     .build();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void authentication으로_등록된_유저를_정상적으로_조회하는지_확인한다() {
    Authentication auth = new UsernamePasswordAuthenticationToken(mockUserId, mockPassword);
    SecurityContextHolder.getContext().setAuthentication(auth);

    when(userRepository.findOneWithAuthoritiesById(mockUserId)).thenReturn(Optional.of(mockUser));
    User returnedUser = userService.getCurrentUser();

    assertEquals(mockUser, returnedUser);
  }

  @Test
  void authentication으로_등록된_유저가_존재하지_않으면_UserNotFoundException_이_발생하는지_확인한다() {
    Authentication auth = new UsernamePasswordAuthenticationToken(mockUserId, mockPassword);
    SecurityContextHolder.getContext().setAuthentication(auth);

    when(userRepository.findOneWithAuthoritiesById(mockUserId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getCurrentUser());
  }
}