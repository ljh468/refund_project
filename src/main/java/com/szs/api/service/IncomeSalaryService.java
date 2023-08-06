package com.szs.api.service;

import com.szs.api.domain.entity.IncomeSalary;

import java.util.List;

public interface IncomeSalaryService {

  void saveAll(List<IncomeSalary> incomeSalaries);

}
