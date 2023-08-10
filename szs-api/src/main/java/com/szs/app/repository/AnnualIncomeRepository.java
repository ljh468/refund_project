package com.szs.app.repository;

import com.szs.app.domain.entity.AnnualIncome;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnualIncomeRepository extends JpaRepository<AnnualIncome, Long> {

  AnnualIncome findByUserIdAndIncomeYearAndIsDeletedFalse(String userId, String valueOf);

  List<AnnualIncome> findAllByUserIdAndIsDeletedFalse(String userId);
}
