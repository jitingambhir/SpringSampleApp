package io.spring.start.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import io.spring.start.domain.PersistentToken;
import io.spring.start.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
