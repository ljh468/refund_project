package com.szs.app.auth;

import com.szs.app.auth.exception.RegistrationNotAllowedException;
import com.szs.app.auth.exception.UserAlreadyExistsException;
import com.szs.app.domain.entity.Authority;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.type.RoleType;
import com.szs.app.repository.UserRepository;
import com.szs.app.service.AllowableUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  private final AllowableUserService allowableUserService;

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(final String userId) {
    return userRepository.findOneWithAuthoritiesById(userId)
                         .filter(User::isActivated)
                         .map(this::getUserDetails)
                         .orElseThrow(() -> new UsernameNotFoundException("userId didn't match"));
  }

  private org.springframework.security.core.userdetails.User getUserDetails(User user) {
    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                                                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                                                    .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(user.getId(),
                                                                  user.getPassword(),
                                                                  grantedAuthorities);
  }

  @Transactional
  public User signUp(String userId, String password, String name, String regNo) {
    LocalDateTime now = LocalDateTime.now();
    String[] regNoArr = regNo.split("-");
    verifyAllowableUser(name, regNoArr[0], regNoArr[1]);
    if (!exists(userId)) {
      return userRepository.save(User.builder()
                                     .id(userId)
                                     .password(passwordEncoder.encode(password))
                                     .name(name)
                                     .regNoFront(regNoArr[0])
                                     .regNoBack(passwordEncoder.encode(regNoArr[1]))
                                     .authorities(Collections.singleton(Authority.builder().authorityName(RoleType.ROLE_USER.name()).build()))
                                     .isActivated(true)
                                     .isDeleted(false)
                                     .createdAt(now)
                                     .updatedAt(now)
                                     .build());
    } else {
      throw new UserAlreadyExistsException(userId);
    }
  }

  private void verifyAllowableUser(String name, String regFront, String regBack) {
    if (!allowableUserService.isExistsByNameAndRegNo(name, regFront, regBack)) {
      throw new RegistrationNotAllowedException("username or registration number not allowed");
    }
  }

  private boolean exists(String userId) {
    return userRepository.existsById(userId);
  }
}