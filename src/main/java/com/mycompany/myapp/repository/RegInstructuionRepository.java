package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegInstructuion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegInstructuion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegInstructuionRepository extends JpaRepository<RegInstructuion, Long> {}
