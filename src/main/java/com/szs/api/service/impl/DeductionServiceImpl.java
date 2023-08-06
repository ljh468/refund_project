package com.szs.api.service.impl;

import com.szs.api.domain.entity.Deduction;
import com.szs.api.repository.DeductionRepository;
import com.szs.api.service.DeductionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeductionServiceImpl implements DeductionService {

  private final DeductionRepository deductionRepository;

  @Override
  @Transactional
  public void saveAll(List<Deduction> deductions) {
    deductionRepository.saveAll(deductions);
  }
}
