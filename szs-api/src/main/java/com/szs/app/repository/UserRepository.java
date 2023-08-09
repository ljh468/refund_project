package com.szs.app.repository;

import com.szs.app.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @EntityGraph(attributePaths = "authorities")
  Optional<User> findOneWithAuthoritiesById(String userId);

}
