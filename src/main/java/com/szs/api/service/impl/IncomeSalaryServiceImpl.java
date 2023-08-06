package com.szs.api.service.impl;

import com.szs.api.domain.entity.IncomeSalary;
import com.szs.api.repository.IncomeSalaryRepository;
import com.szs.api.service.IncomeSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncomeSalaryServiceImpl implements IncomeSalaryService {

  private final IncomeSalaryRepository incomeSalaryRepository;

  @Autowired
  public IncomeSalaryServiceImpl(IncomeSalaryRepository incomeSalaryRepository) {
    this.incomeSalaryRepository = incomeSalaryRepository;
  }

  @Override
  @Transactional
  public void saveAll(List<IncomeSalary> incomeSalaries) {
    incomeSalaryRepository.saveAll(incomeSalaries);
  }
}
