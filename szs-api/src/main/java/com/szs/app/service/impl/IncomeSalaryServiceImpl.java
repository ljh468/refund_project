package com.szs.app.service.impl;

import com.szs.app.domain.entity.IncomeSalary;
import com.szs.app.repository.IncomeSalaryRepository;
import com.szs.app.service.IncomeSalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeSalaryServiceImpl implements IncomeSalaryService {

  private final IncomeSalaryRepository incomeSalaryRepository;

  @Override
  @Transactional
  public void saveAll(List<IncomeSalary> incomeSalaries) {
    incomeSalaryRepository.saveAll(incomeSalaries);
  }
}
