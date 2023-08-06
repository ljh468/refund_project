package com.szs.api.repository;

import com.szs.api.domain.entity.YearEndTaxScrapHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearEndTaxScrapRepository extends JpaRepository<YearEndTaxScrapHistory, Long> {

}
