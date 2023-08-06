package com.szs.api.service.impl;

import com.szs.api.repository.IncomeSalaryRepository;
import com.szs.api.service.IncomSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeSalaryServiceImpl implements IncomSalaryService {

  private final IncomeSalaryRepository incomeSalaryRepository;

  @Autowired
  public IncomeSalaryServiceImpl(IncomeSalaryRepository incomeSalaryRepository) {
    this.incomeSalaryRepository = incomeSalaryRepository;
  }
}
