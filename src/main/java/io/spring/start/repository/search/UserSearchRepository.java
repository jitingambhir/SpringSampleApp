package io.spring.start.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.spring.start.domain.User;

/**
 * Spring Data ElasticSearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
