package com.szs.app.global;

import com.szs.app.domain.entity.AllowableUser;
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
public class AllowableUserApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  private final ResourceLoader resourceLoader;

  private final AllowableUserService allowableUserService;

  @Autowired
  public AllowableUserApplicationListener(ResourceLoader resourceLoader, AllowableUserService allowableUserService) {
    this.resourceLoader = resourceLoader;
    this.allowableUserService = allowableUserService;
  }

  @Override
  public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
    loadAllowableUser();
  }

  private void loadAllowableUser() {
    readJson().forEach(allowableUser -> {
      if (allowableUserService.isExistsByNameAndRegNo(allowableUser.getName(), allowableUser.getRegNoFront(), allowableUser.getRegNoBack())) {
        log.info("AllowableUser already exists in DB :: {}", allowableUser.getName());
        return;
      }
      allowableUserService.create(allowableUser);
    });
  }

  public Stream<AllowableUser> readJson() {
    Stream<AllowableUser> stream = Stream.empty();
    try {
      InputStream inputStream = resourceLoader.getResource("classpath:allowableUser.json").getInputStream();
      stream = fromJsonList(inputStream, AllowableUser.class).stream();
    } catch (IOException | NullPointerException exception) {
      log.error("file not found: {}", exception.getMessage());
    }
    return stream;
  }
}
