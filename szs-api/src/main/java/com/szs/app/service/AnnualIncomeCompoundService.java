package com.szs.app.service;

import com.szs.app.domain.entity.AnnualIncome;

import java.util.List;

public interface AnnualIncomeCompoundService {

  List<AnnualIncome> getCalculatedAnnualIncomesByUserId(String id);

}
