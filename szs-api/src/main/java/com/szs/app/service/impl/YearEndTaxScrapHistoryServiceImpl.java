package com.szs.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szs.app.domain.entity.YearEndTaxScrapHistory;
import com.szs.app.global.scrap.response.ApiResponse;
import com.szs.app.global.scrap.szs.SzsRestApiHelper;
import com.szs.app.repository.YearEndTaxScrapHistoryRepository;
import com.szs.app.service.YearEndTaxScrapHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class YearEndTaxScrapHistoryServiceImpl implements YearEndTaxScrapHistoryService {

  private final YearEndTaxScrapHistoryRepository yearEndTaxScrapHistoryRepository;

  @Override
  public ApiResponse scrap(String name, Integer regNoFront, Integer regNoBack) {
    SzsRestApiHelper apiHelper = new SzsRestApiHelper();
    Map<String, Object> request = apiHelper.newScrapByUserRequest(name, regNoFront+"-"+regNoBack);

    ApiResponse responseObj = null;
    try {
      responseObj = new ObjectMapper().readValue(apiHelper.scrap(request), ApiResponse.class);
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("JsonProcessingException: {}", jsonProcessingException.getMessage());
    }
    return responseObj;
  }

  @Override
  @Transactional
  public YearEndTaxScrapHistory save(YearEndTaxScrapHistory yearEndTaxScrapHistory) {
    yearEndTaxScrapHistoryRepository.save(yearEndTaxScrapHistory);
    return yearEndTaxScrapHistory;
  }
}
