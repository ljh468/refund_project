package com.szs.app.service;

import com.szs.app.domain.entity.YearEndTaxScrapHistory;
import com.szs.app.global.scrap.response.ApiResponse;

public interface YearEndTaxScrapHistoryService {

  YearEndTaxScrapHistory save(YearEndTaxScrapHistory yearEndTaxScrapHistory);

  ApiResponse scrap(String name, String regNoFront, String regNoBack);

}
