package com.szs.api.service;

import com.szs.api.domain.entity.Deduction;

import java.util.List;

public interface DeductionService {

  void saveAll(List<Deduction> deductions);
}
