package com.szs.app.controller;

import com.szs.app.auth.AuthService;
import com.szs.app.auth.exception.BedCredentialsException;
import com.szs.app.auth.exception.RegistrationNotAllowedException;
import com.szs.app.auth.exception.UserAlreadyExistsException;
import com.szs.app.auth.jwt.JwtFilter;
import com.szs.app.auth.jwt.TokenProvider;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.input.LoginInput;
import com.szs.app.domain.input.SignUpInput;
import com.szs.app.domain.response.SignUpResponse;
import com.szs.app.domain.response.TokenResponse;
import com.szs.app.domain.response.UserResponse;
import com.szs.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;

@Slf4j
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class UserController {

  private final TokenProvider tokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final AuthService authService;

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Validated @RequestBody SignUpInput input) {
    try {
      User user = authService.signUp(input.getUserId(),
                                     input.getPassword(),
                                     input.getName(),
                                     input.getRegNo());

      String newToken = tokenProvider.createToken(addAuthentication(input.getUserId(), input.getPassword()));
      HttpHeaders headers = createHeadersWithToken(newToken);
      return new ResponseEntity<>(SignUpResponse.from(user), headers, HttpStatus.OK);

    } catch (UserAlreadyExistsException userAlreadyExistsException) {
      log.debug("user already exists with userId");
      throw userAlreadyExistsException;
    } catch (RegistrationNotAllowedException registrationNotAllowedException) {
      log.debug("registration is not allowed with regNo");
      throw registrationNotAllowedException;
    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw new BedCredentialsException("bad credentials");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Validated @RequestBody LoginInput input) {
    try {
      String newToken = tokenProvider.createToken(addAuthentication(input.getUserId(), input.getPassword()));
      HttpHeaders headers = createHeadersWithToken(newToken);
      return new ResponseEntity<>(new TokenResponse(newToken), headers, HttpStatus.OK);

    } catch (UserAlreadyExistsException userAlreadyExistsException) {
      log.debug("user already exists with userId");
      throw userAlreadyExistsException;
    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw new BedCredentialsException("bad credentials");
    }
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> me() {
    try {
      User currentUser = userService.getCurrentUser();
      return ResponseEntity.ok(UserResponse.from(currentUser));

    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw new BedCredentialsException(runtimeException.getMessage(), runtimeException.getCause());
    }
  }

  private Authentication addAuthentication(String userId, String password) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }

  private HttpHeaders createHeadersWithToken(String token) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);
    return httpHeaders;
  }
}
