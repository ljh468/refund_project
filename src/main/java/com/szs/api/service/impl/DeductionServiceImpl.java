package com.szs.api.service.impl;

import com.szs.api.domain.entity.Deduction;
import com.szs.api.repository.DeductionRepository;
import com.szs.api.service.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeductionServiceImpl implements DeductionService {

  private final DeductionRepository deductionRepository;

  @Autowired
  public DeductionServiceImpl(DeductionRepository deductionRepository) {
    this.deductionRepository = deductionRepository;
  }

  @Override
  @Transactional
  public void saveAll(List<Deduction> deductions) {
    deductionRepository.saveAll(deductions);
  }
}
