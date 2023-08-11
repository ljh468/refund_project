package com.szs.app.config;

import com.szs.app.auth.jwt.*;
import com.szs.app.global.encoder.CustomAesPasswordEncoder;
import com.szs.app.global.encoder.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity // @PreAuthorize 어노테이션을 메서드 단위로 추가하기 위해서 적용
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;

  private final CorsFilter corsFilter;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public CustomPasswordEncoder passwordEncoder() {
    return new CustomAesPasswordEncoder();
  }

  @Bean
  // Spring Security의 기본 설정은 모든 요청에 인증이 필요하도록 되어있습니다.
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        )

        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(PathRequest.toH2Console()).permitAll()
            .antMatchers("/szs/signup", "/szs/login", "/swagger-ui/**", "/swagger-ui.html","/swagger-ui/index.html",
                         "/swagger/**", "/swagger-resources/**", "/webjars/**", "/v3/api-docs/**").permitAll()
            .anyRequest()
            .authenticated()
        )

        // 세션을 사용하지 않기 때문에 STATELESS로 설정
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .apply(new JwtSecurityConfig(tokenProvider));

    return httpSecurity.build();
  }
}
