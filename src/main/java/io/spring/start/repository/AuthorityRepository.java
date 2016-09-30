package io.spring.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.start.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
