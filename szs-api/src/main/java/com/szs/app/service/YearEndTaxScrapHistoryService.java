package com.szs.app.service;

import com.szs.app.domain.entity.YearEndTaxScrapHistory;
import com.szs.app.scrap.response.ApiResponseData;

public interface YearEndTaxScrapHistoryService {

  YearEndTaxScrapHistory save(YearEndTaxScrapHistory yearEndTaxScrapHistory);

  ApiResponseData scrap(String name, String regNo);

}
