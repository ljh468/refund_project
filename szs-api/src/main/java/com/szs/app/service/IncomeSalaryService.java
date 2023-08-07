package com.szs.app.service;

import com.szs.app.domain.entity.IncomeSalary;

import java.util.List;

public interface IncomeSalaryService {

  void saveAll(List<IncomeSalary> incomeSalaries);

}
