package io.spring.start.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import io.spring.start.domain.Company;

/**
 * Spring Data ElasticSearch repository for the Company entity.
 */
public interface CompanySearchRepository extends ElasticsearchRepository<Company, Long> {
}
