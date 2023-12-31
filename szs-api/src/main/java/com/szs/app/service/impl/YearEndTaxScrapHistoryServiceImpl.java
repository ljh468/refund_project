package com.szs.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szs.app.domain.entity.YearEndTaxScrapHistory;
import com.szs.app.scrap.response.ApiResponseData;
import com.szs.app.scrap.szs.SzsRestApiHelper;
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
  public ApiResponseData scrap(String name, String regNo) {
    SzsRestApiHelper apiHelper = new SzsRestApiHelper();
    Map<String, Object> request = apiHelper.newScrapByUserRequest(name, regNo);

    ApiResponseData responseObj = null;
    try {
      responseObj = new ObjectMapper().readValue(apiHelper.scrap(request), ApiResponseData.class);
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("JsonProcessingException: {}", jsonProcessingException.getMessage());
    }
    return responseObj;
  }

  @Override
  @Transactional
  public YearEndTaxScrapHistory save(YearEndTaxScrapHistory yearEndTaxScrapHistory) {
    return yearEndTaxScrapHistoryRepository.save(yearEndTaxScrapHistory);
  }
}
