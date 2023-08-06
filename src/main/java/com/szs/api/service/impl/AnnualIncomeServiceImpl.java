package com.szs.api.service.impl;

import com.szs.api.repository.AnnualIncomeRepository;
import com.szs.api.service.AnnualIncomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnnualIncomeServiceImpl implements AnnualIncomeService {

  private final AnnualIncomeRepository annualIncomeRepository;

  @Autowired
  public AnnualIncomeServiceImpl(AnnualIncomeRepository annualIncomeRepository) {
    this.annualIncomeRepository = annualIncomeRepository;
  }
}
