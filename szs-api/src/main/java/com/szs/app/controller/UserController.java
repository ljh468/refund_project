package com.szs.app.controller;

import com.szs.app.auth.AuthService;
import com.szs.app.auth.exception.BedCredentialsException;
import com.szs.app.auth.exception.RegistrationNotAllowedException;
import com.szs.app.auth.exception.UserAlreadyExistsException;
import com.szs.app.auth.exception.UserNotFoundException;

import com.szs.app.auth.jwt.JwtFilter;
import com.szs.app.auth.jwt.TokenProvider;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.input.LoginInput;
import com.szs.app.domain.input.SignUpInput;
import com.szs.app.domain.response.SignUpResponse;
import com.szs.app.domain.response.TokenResponse;
import com.szs.app.domain.response.UserResponse;
import com.szs.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.szs.app.auth.exception.handler.ErrorCode.E0002;

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
  @Operation(summary = "회원가입", description = "회원가입은 허용된 유저만 가능합니다.")
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
      throw new BedCredentialsException(E0002, "bad credentials");
    }
  }

  @PostMapping("/login")
  @Operation(summary = "로그인", description = "가입한 유저는 로그인하여 JWT 토큰을 발급받습니다.")
  public ResponseEntity<?> login(@Validated @RequestBody LoginInput input) {
    try {
      String newToken = tokenProvider.createToken(addAuthentication(input.getUserId(), input.getPassword()));
      HttpHeaders headers = createHeadersWithToken(newToken);
      return new ResponseEntity<>(new TokenResponse(newToken), headers, HttpStatus.OK);

    } catch (UserNotFoundException userNotFoundException) {
      log.debug("user not found");
      throw userNotFoundException;
    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw new BedCredentialsException(E0002, "bad credentials");
    } catch (Exception exception) {
      log.warn(exception.getMessage(), exception.getCause());
      throw new RuntimeException(exception.getMessage(), exception.getCause());
    }
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "내 정보 조회", description = "로그인한 유저는 자신의 정보를 조회할 수 있습니다.")
  public ResponseEntity<?> me() {
    try {
      User currentUser = userService.getCurrentUser();
      return ResponseEntity.ok(UserResponse.from(currentUser));

    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw new BedCredentialsException(E0002, "bad credentials");
    } catch (Exception exception) {
      log.warn(exception.getMessage(), exception.getCause());
      throw new RuntimeException(exception.getMessage(), exception.getCause());
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
