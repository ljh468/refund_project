package com.szs.app.repository;

import com.szs.app.domain.entity.YearEndTaxScrapHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearEndTaxScrapHistoryRepository extends JpaRepository<YearEndTaxScrapHistory, Long> {

}
