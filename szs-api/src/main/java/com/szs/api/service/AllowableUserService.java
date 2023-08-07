package com.szs.api.service;

import com.szs.api.domain.entity.AllowableUser;

public interface AllowableUserService {

  boolean isExistsByNameAndRegNo(String name, Integer regNoFront, Integer regNoBack);

  void create(AllowableUser allowableUser);

}
