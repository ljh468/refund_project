package com.szs.api.repository;

import com.szs.api.domain.entity.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {

}
