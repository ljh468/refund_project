package com.szs.app.controller;

import com.szs.app.auth.AuthService;
import com.szs.app.auth.exception.UserAlreadyExistsException;
import com.szs.app.auth.jwt.JwtFilter;
import com.szs.app.auth.jwt.TokenProvider;
import com.szs.app.domain.dto.SignInput;
import com.szs.app.domain.dto.SignUpInput;
import com.szs.app.domain.dto.SignUpResponse;
import com.szs.app.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class UserController {

  private final TokenProvider tokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Validated @RequestBody SignUpInput input) {
    try {
      User user = authService.signUp(input.getUserId(),
                                     input.getPassword(),
                                     input.getName(),
                                     input.getRegNo());

      String newToken = tokenProvider.createToken(addAuthentication(input.getUserId(), input.getPassword()));
      HttpHeaders headers = createHeadersWithToken(newToken);
      return new ResponseEntity<>(new SignUpResponse(user), headers, HttpStatus.OK);
    } catch (UserAlreadyExistsException userAlreadyExistsException) {
      log.info("User already exists with userId: {}", input.getUserId());
      throw userAlreadyExistsException;
    } catch (Exception exception) {
      throw exception;
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> signIn(@Validated @RequestBody SignInput input) {
    //FIXME 로그인
    Authentication authentication = addAuthentication(input.getUserId(), input.getPassword());
    String newToken = tokenProvider.createToken(authentication);
    return ResponseEntity.ok(newToken);
  }

  @GetMapping("/me")
  public ResponseEntity<?> me() {
    //FIXME 유저 정보조회
    return ResponseEntity.ok(null);
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
