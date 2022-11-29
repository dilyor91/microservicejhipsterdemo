package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RegInstructuionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RegInstructuion}.
 */
public interface RegInstructuionService {
    /**
     * Save a regInstructuion.
     *
     * @param regInstructuionDTO the entity to save.
     * @return the persisted entity.
     */
    RegInstructuionDTO save(RegInstructuionDTO regInstructuionDTO);

    /**
     * Updates a regInstructuion.
     *
     * @param regInstructuionDTO the entity to update.
     * @return the persisted entity.
     */
    RegInstructuionDTO update(RegInstructuionDTO regInstructuionDTO);

    /**
     * Partially updates a regInstructuion.
     *
     * @param regInstructuionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegInstructuionDTO> partialUpdate(RegInstructuionDTO regInstructuionDTO);

    /**
     * Get all the regInstructuions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegInstructuionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" regInstructuion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegInstructuionDTO> findOne(Long id);

    /**
     * Delete the "id" regInstructuion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
