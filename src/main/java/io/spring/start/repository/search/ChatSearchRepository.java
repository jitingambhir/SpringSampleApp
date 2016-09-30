package io.spring.start.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.spring.start.domain.Chat;

/**
 * Spring Data ElasticSearch repository for the Chat entity.
 */
public interface ChatSearchRepository extends ElasticsearchRepository<Chat, Long> {
}
