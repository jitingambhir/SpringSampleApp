package io.spring.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.spring.start.domain.Company;

/**
 * Spring Data JPA repository for the Company entity.
 */
public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select distinct company from Company company left join fetch company.users")
    List<Company> findAllWithEagerRelationships();

    @Query("select company from Company company left join fetch company.users where company.id =:id")
    Company findOneWithEagerRelationships(@Param("id") Long id);
    
    List<Company> findByUsers_Login(String login);

}
