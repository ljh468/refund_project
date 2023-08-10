package com.szs.app.service;

import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.User;
import com.szs.app.global.scrap.response.ApiResponse;

public interface ScrapedDataService {

  AnnualIncome saveScrappedData(ApiResponse scrap, User user);

}
