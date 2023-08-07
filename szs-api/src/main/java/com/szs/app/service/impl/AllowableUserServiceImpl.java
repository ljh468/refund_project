package com.szs.app.service.impl;

import com.szs.app.domain.entity.AllowableUser;
import com.szs.app.repository.AllowableUserRepository;
import com.szs.app.service.AllowableUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllowableUserServiceImpl implements AllowableUserService {

  private final AllowableUserRepository allowableUserRepository;

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
