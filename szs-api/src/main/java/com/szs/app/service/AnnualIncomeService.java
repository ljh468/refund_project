package com.szs.app.service;

import com.szs.app.domain.entity.AnnualIncome;

import java.util.List;

public interface AnnualIncomeService {

  AnnualIncome save(AnnualIncome annualIncome);

  AnnualIncome findByUserIdAndIncomeYearNotDeleted(String userId, String scrapYear);

  List<AnnualIncome> findAllByUserId(String id);
}
