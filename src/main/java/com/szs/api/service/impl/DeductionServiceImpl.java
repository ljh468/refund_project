package com.szs.api.service.impl;

import com.szs.api.repository.DeductionRepository;
import com.szs.api.service.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeductionServiceImpl implements DeductionService {

  private final DeductionRepository deductionRepository;

  @Autowired
  public DeductionServiceImpl(DeductionRepository deductionRepository) {
    this.deductionRepository = deductionRepository;
  }

}
