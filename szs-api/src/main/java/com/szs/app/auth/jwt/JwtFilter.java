package com.szs.app.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");

  private final TokenProvider tokenProvider;

  // JWT 토큰의 인증정보를 현재 실행중인 Security Context에 저장
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    try {
      getToken(httpRequest)
          .filter(not(String::isEmpty))
          .filter(tokenProvider::validateToken)
          .map(tokenProvider::getAuthentication)
          .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
    } catch (BadCredentialsException ignore) {
    } finally {
      filterChain.doFilter(request, response);
    }
  }

  // Header에서 토큰 정보를 꺼내오기 위한 getToken 메서드
  private Optional<String> getToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                   .filter(not(String::isEmpty))
                   .map(BEARER_PATTERN::matcher)
                   .filter(Matcher::find)
                   .map(matcher -> matcher.group(1));
  }
}
