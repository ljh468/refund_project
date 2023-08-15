package com.szs.app.repository;

import com.szs.app.domain.entity.AnnualIncome;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnualIncomeRepository extends JpaRepository<AnnualIncome, Long> {

  @Query("select a from AnnualIncome a join fetch a.refund where a.user.id = :userId and a.incomeYear = :scrapYear and a.isDeleted = false")
  AnnualIncome findByUserIdAndIncomeYearAndIsDeletedFalse(String userId, String scrapYear);

  @Query("select a from AnnualIncome a join fetch a.refund where a.user.id = :userId and a.isDeleted = false")
  List<AnnualIncome> findAllWithRefundByUserIdAndIsDeletedFalse(String userId);
}
