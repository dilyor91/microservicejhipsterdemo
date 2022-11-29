package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AddmissionRulesRepository;
import com.mycompany.myapp.service.AddmissionRulesService;
import com.mycompany.myapp.service.dto.AddmissionRulesDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AddmissionRules}.
 */
@RestController
@RequestMapping("/api")
public class AddmissionRulesResource {

    private final Logger log = LoggerFactory.getLogger(AddmissionRulesResource.class);

    private static final String ENTITY_NAME = "microservicejhipsterdemoAddmissionRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddmissionRulesService addmissionRulesService;

    private final AddmissionRulesRepository addmissionRulesRepository;

    public AddmissionRulesResource(AddmissionRulesService addmissionRulesService, AddmissionRulesRepository addmissionRulesRepository) {
        this.addmissionRulesService = addmissionRulesService;
        this.addmissionRulesRepository = addmissionRulesRepository;
    }

    /**
     * {@code POST  /addmission-rules} : Create a new addmissionRules.
     *
     * @param addmissionRulesDTO the addmissionRulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addmissionRulesDTO, or with status {@code 400 (Bad Request)} if the addmissionRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/addmission-rules")
    public ResponseEntity<AddmissionRulesDTO> createAddmissionRules(@Valid @RequestBody AddmissionRulesDTO addmissionRulesDTO)
        throws URISyntaxException {
        log.debug("REST request to save AddmissionRules : {}", addmissionRulesDTO);
        if (addmissionRulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addmissionRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddmissionRulesDTO result = addmissionRulesService.save(addmissionRulesDTO);
        return ResponseEntity
            .created(new URI("/api/addmission-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /addmission-rules/:id} : Updates an existing addmissionRules.
     *
     * @param id the id of the addmissionRulesDTO to save.
     * @param addmissionRulesDTO the addmissionRulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addmissionRulesDTO,
     * or with status {@code 400 (Bad Request)} if the addmissionRulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addmissionRulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/addmission-rules/{id}")
    public ResponseEntity<AddmissionRulesDTO> updateAddmissionRules(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AddmissionRulesDTO addmissionRulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AddmissionRules : {}, {}", id, addmissionRulesDTO);
        if (addmissionRulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addmissionRulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addmissionRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddmissionRulesDTO result = addmissionRulesService.update(addmissionRulesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addmissionRulesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /addmission-rules/:id} : Partial updates given fields of an existing addmissionRules, field will ignore if it is null
     *
     * @param id the id of the addmissionRulesDTO to save.
     * @param addmissionRulesDTO the addmissionRulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addmissionRulesDTO,
     * or with status {@code 400 (Bad Request)} if the addmissionRulesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the addmissionRulesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the addmissionRulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/addmission-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AddmissionRulesDTO> partialUpdateAddmissionRules(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AddmissionRulesDTO addmissionRulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AddmissionRules partially : {}, {}", id, addmissionRulesDTO);
        if (addmissionRulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addmissionRulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addmissionRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddmissionRulesDTO> result = addmissionRulesService.partialUpdate(addmissionRulesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addmissionRulesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /addmission-rules} : get all the addmissionRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addmissionRules in body.
     */
    @GetMapping("/addmission-rules")
    public ResponseEntity<List<AddmissionRulesDTO>> getAllAddmissionRules(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AddmissionRules");
        Page<AddmissionRulesDTO> page = addmissionRulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /addmission-rules/:id} : get the "id" addmissionRules.
     *
     * @param id the id of the addmissionRulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addmissionRulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addmission-rules/{id}")
    public ResponseEntity<AddmissionRulesDTO> getAddmissionRules(@PathVariable Long id) {
        log.debug("REST request to get AddmissionRules : {}", id);
        Optional<AddmissionRulesDTO> addmissionRulesDTO = addmissionRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addmissionRulesDTO);
    }

    /**
     * {@code DELETE  /addmission-rules/:id} : delete the "id" addmissionRules.
     *
     * @param id the id of the addmissionRulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/addmission-rules/{id}")
    public ResponseEntity<Void> deleteAddmissionRules(@PathVariable Long id) {
        log.debug("REST request to delete AddmissionRules : {}", id);
        addmissionRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
