package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AddmissionRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AddmissionRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddmissionRulesRepository extends JpaRepository<AddmissionRules, Long> {}
