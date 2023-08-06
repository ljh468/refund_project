package com.szs.api.repository;

import com.szs.api.domain.entity.AllowableUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowableUserRepository extends JpaRepository<AllowableUser, Long> {

}
