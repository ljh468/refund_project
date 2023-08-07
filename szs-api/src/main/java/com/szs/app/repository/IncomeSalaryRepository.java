package com.szs.app.repository;

import com.szs.app.domain.entity.IncomeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeSalaryRepository extends JpaRepository<IncomeSalary, Long> {

}
