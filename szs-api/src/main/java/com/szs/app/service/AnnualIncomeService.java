package com.szs.app.service;

import com.szs.app.domain.entity.AnnualIncome;

public interface AnnualIncomeService {

  AnnualIncome save(AnnualIncome annualIncome);

  AnnualIncome findByUserIdAndIncomeYearNotDeleted(String userId, String valueOf);
}
