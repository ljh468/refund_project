package com.szs.app.service.impl;

import com.szs.app.auth.exception.UserNotFoundException;
import com.szs.app.domain.entity.User;
import com.szs.app.repository.UserRepository;
import com.szs.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getCurrentUser() {
    AtomicReference<String> userId = new AtomicReference<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (isNull(authentication)) {
      log.debug("authentication is null");
      return null;
    }
    userId.set(authentication.getName());
    return userRepository.findOneWithAuthoritiesById(userId.get())
                         .orElseThrow(() -> new UserNotFoundException("User not found"));
  }
}
