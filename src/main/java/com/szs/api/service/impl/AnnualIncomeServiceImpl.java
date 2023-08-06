package com.szs.api.service.impl;

import com.szs.api.domain.entity.AnnualIncome;
import com.szs.api.repository.AnnualIncomeRepository;
import com.szs.api.service.AnnualIncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnualIncomeServiceImpl implements AnnualIncomeService {

  private final AnnualIncomeRepository annualIncomeRepository;

  @Override
  @Transactional
  public void save(AnnualIncome annualIncome) {
    annualIncomeRepository.save(annualIncome);
  }
}
