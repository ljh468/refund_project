package com.szs.app.controller;

import com.szs.api.domain.dto.SignUpInput;
import com.szs.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(SignUpInput signUpInput) {
    //FIXME 회원가입
    return ResponseEntity.ok(null);
  }
}
