package io.spring.start.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.spring.start.domain.ChatSession;

/**
 * Spring Data JPA repository for the ChatSession entity.
 */
@SuppressWarnings("unused")
public interface ChatSessionRepository extends JpaRepository<ChatSession,Long> {
	
}
