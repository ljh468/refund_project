package com.szs.app.service;

import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.User;
import com.szs.app.scrap.response.ApiResponseData;

public interface ScrapedDataService {

  AnnualIncome saveScrappedData(ApiResponseData scrap, User user);

}
