package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RegInstructuion;
import com.mycompany.myapp.repository.RegInstructuionRepository;
import com.mycompany.myapp.service.dto.RegInstructuionDTO;
import com.mycompany.myapp.service.mapper.RegInstructuionMapper;
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
 * Integration tests for the {@link RegInstructuionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegInstructuionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/reg-instructuions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegInstructuionRepository regInstructuionRepository;

    @Autowired
    private RegInstructuionMapper regInstructuionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegInstructuionMockMvc;

    private RegInstructuion regInstructuion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegInstructuion createEntity(EntityManager em) {
        RegInstructuion regInstructuion = new RegInstructuion()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .status(DEFAULT_STATUS);
        return regInstructuion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegInstructuion createUpdatedEntity(EntityManager em) {
        RegInstructuion regInstructuion = new RegInstructuion()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        return regInstructuion;
    }

    @BeforeEach
    public void initTest() {
        regInstructuion = createEntity(em);
    }

    @Test
    @Transactional
    void createRegInstructuion() throws Exception {
        int databaseSizeBeforeCreate = regInstructuionRepository.findAll().size();
        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);
        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeCreate + 1);
        RegInstructuion testRegInstructuion = regInstructuionList.get(regInstructuionList.size() - 1);
        assertThat(testRegInstructuion.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testRegInstructuion.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testRegInstructuion.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testRegInstructuion.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testRegInstructuion.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testRegInstructuion.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testRegInstructuion.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRegInstructuionWithExistingId() throws Exception {
        // Create the RegInstructuion with an existing ID
        regInstructuion.setId(1L);
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        int databaseSizeBeforeCreate = regInstructuionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setTitleUz(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setTitleRu(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setTitleKr(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setContentUz(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setContentRu(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setContentKr(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = regInstructuionRepository.findAll().size();
        // set the field null
        regInstructuion.setStatus(null);

        // Create the RegInstructuion, which fails.
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        restRegInstructuionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegInstructuions() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        // Get all the regInstructuionList
        restRegInstructuionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regInstructuion.getId().intValue())))
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
    void getRegInstructuion() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        // Get the regInstructuion
        restRegInstructuionMockMvc
            .perform(get(ENTITY_API_URL_ID, regInstructuion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regInstructuion.getId().intValue()))
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
    void getNonExistingRegInstructuion() throws Exception {
        // Get the regInstructuion
        restRegInstructuionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegInstructuion() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();

        // Update the regInstructuion
        RegInstructuion updatedRegInstructuion = regInstructuionRepository.findById(regInstructuion.getId()).get();
        // Disconnect from session so that the updates on updatedRegInstructuion are not directly saved in db
        em.detach(updatedRegInstructuion);
        updatedRegInstructuion
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(updatedRegInstructuion);

        restRegInstructuionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regInstructuionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isOk());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
        RegInstructuion testRegInstructuion = regInstructuionList.get(regInstructuionList.size() - 1);
        assertThat(testRegInstructuion.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testRegInstructuion.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testRegInstructuion.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testRegInstructuion.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testRegInstructuion.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testRegInstructuion.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testRegInstructuion.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regInstructuionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegInstructuionWithPatch() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();

        // Update the regInstructuion using partial update
        RegInstructuion partialUpdatedRegInstructuion = new RegInstructuion();
        partialUpdatedRegInstructuion.setId(regInstructuion.getId());

        partialUpdatedRegInstructuion.titleRu(UPDATED_TITLE_RU).contentRu(UPDATED_CONTENT_RU).status(UPDATED_STATUS);

        restRegInstructuionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegInstructuion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegInstructuion))
            )
            .andExpect(status().isOk());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
        RegInstructuion testRegInstructuion = regInstructuionList.get(regInstructuionList.size() - 1);
        assertThat(testRegInstructuion.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testRegInstructuion.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testRegInstructuion.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testRegInstructuion.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testRegInstructuion.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testRegInstructuion.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testRegInstructuion.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRegInstructuionWithPatch() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();

        // Update the regInstructuion using partial update
        RegInstructuion partialUpdatedRegInstructuion = new RegInstructuion();
        partialUpdatedRegInstructuion.setId(regInstructuion.getId());

        partialUpdatedRegInstructuion
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);

        restRegInstructuionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegInstructuion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegInstructuion))
            )
            .andExpect(status().isOk());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
        RegInstructuion testRegInstructuion = regInstructuionList.get(regInstructuionList.size() - 1);
        assertThat(testRegInstructuion.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testRegInstructuion.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testRegInstructuion.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testRegInstructuion.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testRegInstructuion.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testRegInstructuion.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testRegInstructuion.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regInstructuionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegInstructuion() throws Exception {
        int databaseSizeBeforeUpdate = regInstructuionRepository.findAll().size();
        regInstructuion.setId(count.incrementAndGet());

        // Create the RegInstructuion
        RegInstructuionDTO regInstructuionDTO = regInstructuionMapper.toDto(regInstructuion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegInstructuionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regInstructuionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegInstructuion in the database
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegInstructuion() throws Exception {
        // Initialize the database
        regInstructuionRepository.saveAndFlush(regInstructuion);

        int databaseSizeBeforeDelete = regInstructuionRepository.findAll().size();

        // Delete the regInstructuion
        restRegInstructuionMockMvc
            .perform(delete(ENTITY_API_URL_ID, regInstructuion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegInstructuion> regInstructuionList = regInstructuionRepository.findAll();
        assertThat(regInstructuionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
