package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RegInstructuionRepository;
import com.mycompany.myapp.service.RegInstructuionService;
import com.mycompany.myapp.service.dto.RegInstructuionDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.RegInstructuion}.
 */
@RestController
@RequestMapping("/api")
public class RegInstructuionResource {

    private final Logger log = LoggerFactory.getLogger(RegInstructuionResource.class);

    private static final String ENTITY_NAME = "microservicejhipsterdemoRegInstructuion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegInstructuionService regInstructuionService;

    private final RegInstructuionRepository regInstructuionRepository;

    public RegInstructuionResource(RegInstructuionService regInstructuionService, RegInstructuionRepository regInstructuionRepository) {
        this.regInstructuionService = regInstructuionService;
        this.regInstructuionRepository = regInstructuionRepository;
    }

    /**
     * {@code POST  /reg-instructuions} : Create a new regInstructuion.
     *
     * @param regInstructuionDTO the regInstructuionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regInstructuionDTO, or with status {@code 400 (Bad Request)} if the regInstructuion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reg-instructuions")
    public ResponseEntity<RegInstructuionDTO> createRegInstructuion(@Valid @RequestBody RegInstructuionDTO regInstructuionDTO)
        throws URISyntaxException {
        log.debug("REST request to save RegInstructuion : {}", regInstructuionDTO);
        if (regInstructuionDTO.getId() != null) {
            throw new BadRequestAlertException("A new regInstructuion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegInstructuionDTO result = regInstructuionService.save(regInstructuionDTO);
        return ResponseEntity
            .created(new URI("/api/reg-instructuions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reg-instructuions/:id} : Updates an existing regInstructuion.
     *
     * @param id the id of the regInstructuionDTO to save.
     * @param regInstructuionDTO the regInstructuionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regInstructuionDTO,
     * or with status {@code 400 (Bad Request)} if the regInstructuionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regInstructuionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reg-instructuions/{id}")
    public ResponseEntity<RegInstructuionDTO> updateRegInstructuion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegInstructuionDTO regInstructuionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RegInstructuion : {}, {}", id, regInstructuionDTO);
        if (regInstructuionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regInstructuionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regInstructuionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegInstructuionDTO result = regInstructuionService.update(regInstructuionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regInstructuionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reg-instructuions/:id} : Partial updates given fields of an existing regInstructuion, field will ignore if it is null
     *
     * @param id the id of the regInstructuionDTO to save.
     * @param regInstructuionDTO the regInstructuionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regInstructuionDTO,
     * or with status {@code 400 (Bad Request)} if the regInstructuionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the regInstructuionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the regInstructuionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reg-instructuions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegInstructuionDTO> partialUpdateRegInstructuion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegInstructuionDTO regInstructuionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegInstructuion partially : {}, {}", id, regInstructuionDTO);
        if (regInstructuionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regInstructuionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regInstructuionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegInstructuionDTO> result = regInstructuionService.partialUpdate(regInstructuionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regInstructuionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reg-instructuions} : get all the regInstructuions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regInstructuions in body.
     */
    @GetMapping("/reg-instructuions")
    public ResponseEntity<List<RegInstructuionDTO>> getAllRegInstructuions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RegInstructuions");
        Page<RegInstructuionDTO> page = regInstructuionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reg-instructuions/:id} : get the "id" regInstructuion.
     *
     * @param id the id of the regInstructuionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regInstructuionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reg-instructuions/{id}")
    public ResponseEntity<RegInstructuionDTO> getRegInstructuion(@PathVariable Long id) {
        log.debug("REST request to get RegInstructuion : {}", id);
        Optional<RegInstructuionDTO> regInstructuionDTO = regInstructuionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regInstructuionDTO);
    }

    /**
     * {@code DELETE  /reg-instructuions/:id} : delete the "id" regInstructuion.
     *
     * @param id the id of the regInstructuionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reg-instructuions/{id}")
    public ResponseEntity<Void> deleteRegInstructuion(@PathVariable Long id) {
        log.debug("REST request to delete RegInstructuion : {}", id);
        regInstructuionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
