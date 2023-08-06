package com.szs.api.service.impl;

import com.szs.api.domain.entity.AllowableUser;
import com.szs.api.repository.AllowableUserRepository;
import com.szs.api.service.AllowableUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AllowableUserServiceImpl implements AllowableUserService {

  private final AllowableUserRepository allowableUserRepository;

  @Autowired
  public AllowableUserServiceImpl(AllowableUserRepository allowableUserRepository) {
    this.allowableUserRepository = allowableUserRepository;
  }

  @Transactional
  public boolean isExistsByNameAndRegNo(String name, Integer regNoFront, Integer regNoBack) {
    return allowableUserRepository.existsByNameAndRegNo(name, regNoFront, regNoBack);
  }

  @Override
  public void create(AllowableUser allowableUser) {
    allowableUserRepository.save(AllowableUser.builder()
                                              .name(allowableUser.getName())
                                              .regNoFront(allowableUser.getRegNoFront())
                                              .regNoBack(allowableUser.getRegNoBack())
                                              .build());
  }
}
