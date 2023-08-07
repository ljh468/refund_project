package com.szs.app.repository;

import com.szs.app.domain.entity.AnnualIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualIncomeRepository extends JpaRepository<AnnualIncome, Long> {

}
