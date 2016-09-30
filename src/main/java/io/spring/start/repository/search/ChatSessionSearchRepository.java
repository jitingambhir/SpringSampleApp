package io.spring.start.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.spring.start.domain.ChatSession;

/**
 * Spring Data ElasticSearch repository for the ChatSession entity.
 */
public interface ChatSessionSearchRepository extends ElasticsearchRepository<ChatSession, Long> {
}
