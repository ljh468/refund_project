package com.szs.app.auth.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// JwtFilter와 TokenProvider를 SecurityConfig에 적용하기 위한 클래스
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private TokenProvider tokenProvider;

  public JwtSecurityConfig(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void configure(HttpSecurity httpSecurity) {
    // 사용자 정의 JwtFilter를 시큐리티 필터 체인에 추가
    // httpSecurity는 프로토타입 빈 (요청시마다 새로 생성)
    // HttpSecurity는 빌더 또는 도메인 특정 언어에 가까움
    // 보안 필터 체인 구축용. 구성이 처리될 때마다 HttpSecurity의 새 인스턴스가 사용
    httpSecurity.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
  }
}
