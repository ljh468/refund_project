package com.szs.api.repository;

import com.szs.api.domain.entity.ScrapHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<ScrapHistory, Long> {

}
