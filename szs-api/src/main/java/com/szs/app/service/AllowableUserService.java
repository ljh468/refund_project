package com.szs.app.service;

import com.szs.app.domain.entity.AllowableUser;

public interface AllowableUserService {

  boolean isExistsByNameAndRegNo(String name, Integer regNoFront, Integer regNoBack);

  void create(AllowableUser allowableUser);

}
