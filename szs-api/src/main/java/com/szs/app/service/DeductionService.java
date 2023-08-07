package com.szs.app.service;

import com.szs.app.domain.entity.Deduction;

import java.util.List;

public interface DeductionService {

  void saveAll(List<Deduction> deductions);
}
