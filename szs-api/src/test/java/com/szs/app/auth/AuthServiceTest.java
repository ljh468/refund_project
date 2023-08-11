package com.szs.app.auth;

import com.szs.app.auth.exception.UserAlreadyExistsException;
import com.szs.app.auth.exception.UserNotFoundException;
import com.szs.app.global.encoder.CustomPasswordEncoder;
import com.szs.app.domain.entity.Authority;
import com.szs.app.domain.entity.User;
import com.szs.app.repository.UserRepository;
import com.szs.app.service.AllowableUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

  @InjectMocks
  AuthService authService;

  @Mock
  UserRepository userRepository;

  @Mock
  AllowableUserService allowableUserService;

  @Mock
  private CustomPasswordEncoder passwordEncoder;

  private static final String mockUserId = "mockUserId";

  private static final String mockPassword = "password123^^";

  private static final String mockName = "mockName";

  private static final Authority mockAuthority = Authority.builder().authorityName("ROLE_USER").build();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authService = new AuthService(passwordEncoder, allowableUserService, userRepository);
  }

  @Test
  public void 활성화된_유저로_loadUserByUsername가_호출될_때_UserDetails를_정상적으로_반환하는지_확인한다() {
    User user = User.builder()
                    .id(mockUserId)
                    .password(mockPassword)
                    .authorities(Set.of(mockAuthority))
                    .isActivated(true)
                    .build();

    when(userRepository.findOneWithAuthoritiesById(mockUserId)).thenReturn(Optional.of(user));

    org.springframework.security.core.userdetails.User userDetails =
        (org.springframework.security.core.userdetails.User) authService.loadUserByUsername(mockUserId);

    assertEquals(mockUserId, userDetails.getUsername());
    assertTrue(userDetails.getAuthorities().stream()
                          .anyMatch(auth -> "ROLE_USER".equals(auth.getAuthority())));
  }

  @Test
  public void 활성화되지_않은_유저로_loadUserByUsername가_호출될_때_UserNotFoundException이_발생하는지_확인한다() {
    User user = User.builder()
                    .id(mockUserId)
                    .password(mockPassword)
                    .isActivated(false)
                    .build();

    when(userRepository.findOneWithAuthoritiesById(mockUserId)).thenReturn(Optional.of(user));

    assertThrows(UserNotFoundException.class, () -> authService.loadUserByUsername(mockUserId));
  }

  @Test
  public void 권한이_없는_유저로_loadUserByUsername가_호출될_때_UserNotFoundException이_발생하는지_확인한다() {
    when(userRepository.findOneWithAuthoritiesById(mockUserId)).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, () -> authService.loadUserByUsername(mockUserId));
  }

  @Test
  public void 가입한_유저가_없으면_회원가입에_성공한다() {
    String regNo = "12345-67890";
    String[] split = regNo.split("-");

    User user = User.builder()
                    .id(mockUserId)
                    .password(mockPassword)
                    .name(mockName)
                    .regNoFront(split[0])
                    .regNoBack(split[1])
                    .isDeleted(false)
                    .isActivated(false)
                    .build();

    when(allowableUserService.isExistsByNameAndRegNo(mockName, split[0], split[1])).thenReturn(true);
    when(userRepository.existsById(mockUserId)).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    User result = authService.signUp(mockUserId, mockPassword, mockName, regNo);
    assertEquals(mockUserId, result.getId());
    assertEquals(mockName, result.getName());
    assertEquals(split[0] + split[1], result.getRegNoFront() + result.getRegNoBack());

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  public void 가입한_유저가_존재하면_UserAlreadyExistsException이_발생하는지_확인한다() {
    String regNo = "12345-67890";
    String[] split = regNo.split("-");

    when(userRepository.existsById(mockUserId)).thenReturn(true);
    when(allowableUserService.isExistsByNameAndRegNo(mockName, split[0], split[1])).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> {
      authService.signUp(mockUserId, mockPassword, mockName, regNo);
    });
  }
}