package com.szs.api.service;

import com.szs.api.domain.entity.YearEndTaxScrapHistory;
import com.szs.api.global.scrap.response.ApiResponse;

public interface YearEndTaxScrapHistoryService {

  YearEndTaxScrapHistory save(YearEndTaxScrapHistory yearEndTaxScrapHistory);

  ApiResponse scrap(String name, Integer regNoFront, Integer regNoBack);

}
