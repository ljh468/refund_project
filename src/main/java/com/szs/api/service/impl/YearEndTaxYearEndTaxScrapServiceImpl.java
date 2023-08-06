package com.szs.api.service.impl;

import com.szs.api.repository.YearEndTaxScrapRepository;
import com.szs.api.service.YearEndTaxScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YearEndTaxYearEndTaxScrapServiceImpl implements YearEndTaxScrapService {

  private final YearEndTaxScrapRepository yearEndTaxScrapRepository;

  @Autowired
  public YearEndTaxYearEndTaxScrapServiceImpl(YearEndTaxScrapRepository yearEndTaxScrapRepository) {
    this.yearEndTaxScrapRepository = yearEndTaxScrapRepository;
  }

}
