package com.szs.app.service.impl;

import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.repository.AnnualIncomeRepository;
import com.szs.app.service.AnnualIncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnualIncomeServiceImpl implements AnnualIncomeService {

  private final AnnualIncomeRepository annualIncomeRepository;

  @Override
  @Transactional
  public AnnualIncome save(AnnualIncome annualIncome) {
    return annualIncomeRepository.save(annualIncome);
  }

  @Override
  public AnnualIncome findByUserIdAndIncomeYearNotDeleted(String userId, String scrapYear) {
    return annualIncomeRepository.findByUserIdAndIncomeYearAndIsDeletedFalse(userId, scrapYear);
  }

  @Override
  public List<AnnualIncome> findAllWithRefundByUserId(String userId) {
    return annualIncomeRepository.findAllWithRefundByUserIdAndIsDeletedFalse(userId);
  }
}
