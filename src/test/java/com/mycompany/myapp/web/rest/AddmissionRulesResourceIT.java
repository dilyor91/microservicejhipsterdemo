package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AddmissionRules;
import com.mycompany.myapp.repository.AddmissionRulesRepository;
import com.mycompany.myapp.service.dto.AddmissionRulesDTO;
import com.mycompany.myapp.service.mapper.AddmissionRulesMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AddmissionRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddmissionRulesResourceIT {

    private static final String DEFAULT_TITLE_UZ = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_KR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_KR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_UZ = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_RU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_RU = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_KR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_KR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/addmission-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddmissionRulesRepository addmissionRulesRepository;

    @Autowired
    private AddmissionRulesMapper addmissionRulesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddmissionRulesMockMvc;

    private AddmissionRules addmissionRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddmissionRules createEntity(EntityManager em) {
        AddmissionRules addmissionRules = new AddmissionRules()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .status(DEFAULT_STATUS);
        return addmissionRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddmissionRules createUpdatedEntity(EntityManager em) {
        AddmissionRules addmissionRules = new AddmissionRules()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        return addmissionRules;
    }

    @BeforeEach
    public void initTest() {
        addmissionRules = createEntity(em);
    }

    @Test
    @Transactional
    void createAddmissionRules() throws Exception {
        int databaseSizeBeforeCreate = addmissionRulesRepository.findAll().size();
        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);
        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeCreate + 1);
        AddmissionRules testAddmissionRules = addmissionRulesList.get(addmissionRulesList.size() - 1);
        assertThat(testAddmissionRules.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testAddmissionRules.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testAddmissionRules.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testAddmissionRules.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testAddmissionRules.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testAddmissionRules.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testAddmissionRules.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createAddmissionRulesWithExistingId() throws Exception {
        // Create the AddmissionRules with an existing ID
        addmissionRules.setId(1L);
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        int databaseSizeBeforeCreate = addmissionRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setTitleUz(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setTitleRu(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setTitleKr(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setContentUz(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setContentRu(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setContentKr(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = addmissionRulesRepository.findAll().size();
        // set the field null
        addmissionRules.setStatus(null);

        // Create the AddmissionRules, which fails.
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAddmissionRules() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        // Get all the addmissionRulesList
        restAddmissionRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addmissionRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].titleKr").value(hasItem(DEFAULT_TITLE_KR)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].contentKr").value(hasItem(DEFAULT_CONTENT_KR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getAddmissionRules() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        // Get the addmissionRules
        restAddmissionRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, addmissionRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addmissionRules.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.titleKr").value(DEFAULT_TITLE_KR))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.contentKr").value(DEFAULT_CONTENT_KR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAddmissionRules() throws Exception {
        // Get the addmissionRules
        restAddmissionRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddmissionRules() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();

        // Update the addmissionRules
        AddmissionRules updatedAddmissionRules = addmissionRulesRepository.findById(addmissionRules.getId()).get();
        // Disconnect from session so that the updates on updatedAddmissionRules are not directly saved in db
        em.detach(updatedAddmissionRules);
        updatedAddmissionRules
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(updatedAddmissionRules);

        restAddmissionRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addmissionRulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
        AddmissionRules testAddmissionRules = addmissionRulesList.get(addmissionRulesList.size() - 1);
        assertThat(testAddmissionRules.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testAddmissionRules.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testAddmissionRules.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testAddmissionRules.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testAddmissionRules.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testAddmissionRules.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testAddmissionRules.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addmissionRulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddmissionRulesWithPatch() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();

        // Update the addmissionRules using partial update
        AddmissionRules partialUpdatedAddmissionRules = new AddmissionRules();
        partialUpdatedAddmissionRules.setId(addmissionRules.getId());

        partialUpdatedAddmissionRules.titleUz(UPDATED_TITLE_UZ).titleKr(UPDATED_TITLE_KR).contentKr(UPDATED_CONTENT_KR);

        restAddmissionRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddmissionRules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddmissionRules))
            )
            .andExpect(status().isOk());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
        AddmissionRules testAddmissionRules = addmissionRulesList.get(addmissionRulesList.size() - 1);
        assertThat(testAddmissionRules.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testAddmissionRules.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testAddmissionRules.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testAddmissionRules.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testAddmissionRules.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testAddmissionRules.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testAddmissionRules.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAddmissionRulesWithPatch() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();

        // Update the addmissionRules using partial update
        AddmissionRules partialUpdatedAddmissionRules = new AddmissionRules();
        partialUpdatedAddmissionRules.setId(addmissionRules.getId());

        partialUpdatedAddmissionRules
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);

        restAddmissionRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddmissionRules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddmissionRules))
            )
            .andExpect(status().isOk());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
        AddmissionRules testAddmissionRules = addmissionRulesList.get(addmissionRulesList.size() - 1);
        assertThat(testAddmissionRules.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testAddmissionRules.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testAddmissionRules.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testAddmissionRules.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testAddmissionRules.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testAddmissionRules.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testAddmissionRules.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addmissionRulesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddmissionRules() throws Exception {
        int databaseSizeBeforeUpdate = addmissionRulesRepository.findAll().size();
        addmissionRules.setId(count.incrementAndGet());

        // Create the AddmissionRules
        AddmissionRulesDTO addmissionRulesDTO = addmissionRulesMapper.toDto(addmissionRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddmissionRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addmissionRulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddmissionRules in the database
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddmissionRules() throws Exception {
        // Initialize the database
        addmissionRulesRepository.saveAndFlush(addmissionRules);

        int databaseSizeBeforeDelete = addmissionRulesRepository.findAll().size();

        // Delete the addmissionRules
        restAddmissionRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, addmissionRules.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddmissionRules> addmissionRulesList = addmissionRulesRepository.findAll();
        assertThat(addmissionRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
