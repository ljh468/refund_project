package com.szs.api.service;

import com.szs.api.domain.entity.User;
import com.szs.api.global.scrap.response.ApiResponse;

public interface ScrapedDataService {

  void saveScrappedData(ApiResponse scrap, User user);

}
