package com.szs.app.global.listener;

import com.szs.app.domain.entity.AllowableUser;
import com.szs.app.domain.entity.Authority;
import com.szs.app.repository.AuthorityRepository;
import com.szs.app.service.AllowableUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static com.szs.util.JsonUtils.fromJsonList;


@Slf4j
@Component
public class AuthorityApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  private final ResourceLoader resourceLoader;

  private final AuthorityRepository authorityRepository;

  @Autowired
  public AuthorityApplicationListener(ResourceLoader resourceLoader, AuthorityRepository authorityRepository) {
    this.resourceLoader = resourceLoader;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
    loadAllowableUser();
  }

  private void loadAllowableUser() {
    readJson().forEach(authority -> {
      if (authorityRepository.existsById(authority.getAuthorityName())) {
        log.info("Authority already exists in DB :: {}", authority.getAuthorityName());
        return;
      }
      authorityRepository.save(authority);
    });
  }

  public Stream<Authority> readJson() {
    Stream<Authority> stream = Stream.empty();
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:authority.json").getInputStream();
      stream = fromJsonList(inputStream, Authority.class).stream();
    } catch (IOException | NullPointerException exception) {
      log.error("file not found: {}", exception.getMessage());
    }
    return stream;
  }
}
