package com.szs.app.service;

import com.szs.app.domain.entity.User;
import com.szs.app.global.scrap.response.ApiResponse;

public interface ScrapedDataService {

  void saveScrappedData(ApiResponse scrap, User user);

}
