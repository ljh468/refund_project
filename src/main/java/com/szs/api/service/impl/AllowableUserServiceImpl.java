package com.szs.api.service.impl;

import com.szs.api.repository.AllowableUserRepository;
import com.szs.api.service.AllowableUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AllowableUserServiceImpl implements AllowableUserService {

  private final AllowableUserRepository allowableUserRepository;

  @Autowired
  public AllowableUserServiceImpl(AllowableUserRepository allowableUserRepository) {
    this.allowableUserRepository = allowableUserRepository;
  }

}
