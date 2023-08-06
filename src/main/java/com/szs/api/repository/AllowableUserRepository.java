package com.szs.api.repository;

import com.szs.api.domain.entity.AllowableUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowableUserRepository extends JpaRepository<AllowableUser, Long> {

  @Query("select case when count(u) > 0 then true else false end from AllowableUser u where u.name = :name and u.regNoFront = :regNoFront and u.regNoBack = :regNoBack")
  boolean existsByNameAndRegNo(@Param("name") String name, @Param("regNoFront") Integer regNoFront, @Param("regNoBack") Integer regNoBack);
}
