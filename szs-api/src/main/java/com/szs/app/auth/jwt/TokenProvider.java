package com.szs.app.auth.jwt;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
// 토큰 생성 & 유효성 검증을 담당 TokenProvider
public class TokenProvider implements InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";

  private static final String ACCESS_TOKEN_SUBJECT = "jarvis-access-token";

  private static final String TOKEN_CLAIM_USER_ID = "userId";

  private final String accessTokenIssuer;

  private final Duration accessTokenExpiration;

  private Key key;

  @Autowired
  public TokenProvider(@Value("${szs.security.access-token-issuer}") String accessTokenIssuer,
                       @Value("${szs.security.access-token-expiration}") Duration accessTokenExpiration) {
    this.accessTokenIssuer = accessTokenIssuer;
    this.accessTokenExpiration = accessTokenExpiration;
  }

  // 의존성주입을 받은 후 주입받은 secret 값을 Base64 Decode해서 keyBytes에 할당
  @Override
  public void afterPropertiesSet() {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenIssuer));
  }

  // User 객체의 권한정보를 이용해서 토큰을 생성하는 메소드
  public String createToken(Authentication authentication) {
    Instant now = Instant.now();
    Date newAccessTokenExpiresAt = Date.from(now.plus(accessTokenExpiration));
    String authorities = authentication.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.joining(","));
    return Jwts.builder()
               .setSubject(ACCESS_TOKEN_SUBJECT)
               .claim(TOKEN_CLAIM_USER_ID, authentication.getName())
               .claim(AUTHORITIES_KEY, authorities)
               .signWith(key, SignatureAlgorithm.HS512)
               .setIssuedAt(Date.from(now))
               .setExpiration(newAccessTokenExpiresAt)
               .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                                                               .map(SimpleGrantedAuthority::new)
                                                               .collect(Collectors.toList());
    
    User principal = new User(claims.get(TOKEN_CLAIM_USER_ID).toString(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.debug("Invalid signature jwt token");
    } catch (ExpiredJwtException e) {
      log.debug("Expired jwt token");
    } catch (UnsupportedJwtException e) {
      log.debug("Unsupported jwt token");
    } catch (IllegalArgumentException e) {
      log.debug("Invalid JWT token");
    }
    return false;
  }
}
