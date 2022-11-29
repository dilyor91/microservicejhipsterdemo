package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Timetable;
import com.mycompany.myapp.repository.TimetableRepository;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.mapper.TimetableMapper;
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
 * Integration tests for the {@link TimetableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimetableResourceIT {

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

    private static final String ENTITY_API_URL = "/api/timetables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TimetableMapper timetableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimetableMockMvc;

    private Timetable timetable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timetable createEntity(EntityManager em) {
        Timetable timetable = new Timetable()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .titleKr(DEFAULT_TITLE_KR)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .contentKr(DEFAULT_CONTENT_KR)
            .status(DEFAULT_STATUS);
        return timetable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timetable createUpdatedEntity(EntityManager em) {
        Timetable timetable = new Timetable()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        return timetable;
    }

    @BeforeEach
    public void initTest() {
        timetable = createEntity(em);
    }

    @Test
    @Transactional
    void createTimetable() throws Exception {
        int databaseSizeBeforeCreate = timetableRepository.findAll().size();
        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);
        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isCreated());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate + 1);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testTimetable.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testTimetable.getTitleKr()).isEqualTo(DEFAULT_TITLE_KR);
        assertThat(testTimetable.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testTimetable.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testTimetable.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testTimetable.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createTimetableWithExistingId() throws Exception {
        // Create the Timetable with an existing ID
        timetable.setId(1L);
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        int databaseSizeBeforeCreate = timetableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setTitleUz(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setTitleRu(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setTitleKr(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setContentUz(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setContentRu(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentKrIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setContentKr(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setStatus(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTimetables() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetable.getId().intValue())))
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
    void getTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        // Get the timetable
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL_ID, timetable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timetable.getId().intValue()))
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
    void getNonExistingTimetable() throws Exception {
        // Get the timetable
        restTimetableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();

        // Update the timetable
        Timetable updatedTimetable = timetableRepository.findById(timetable.getId()).get();
        // Disconnect from session so that the updates on updatedTimetable are not directly saved in db
        em.detach(updatedTimetable);
        updatedTimetable
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);
        TimetableDTO timetableDTO = timetableMapper.toDto(updatedTimetable);

        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTimetable.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTimetable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimetable.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTimetable.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTimetable.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testTimetable.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimetableWithPatch() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();

        // Update the timetable using partial update
        Timetable partialUpdatedTimetable = new Timetable();
        partialUpdatedTimetable.setId(timetable.getId());

        partialUpdatedTimetable.titleKr(UPDATED_TITLE_KR).contentUz(UPDATED_CONTENT_UZ);

        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimetable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimetable))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testTimetable.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testTimetable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimetable.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTimetable.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testTimetable.getContentKr()).isEqualTo(DEFAULT_CONTENT_KR);
        assertThat(testTimetable.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTimetableWithPatch() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();

        // Update the timetable using partial update
        Timetable partialUpdatedTimetable = new Timetable();
        partialUpdatedTimetable.setId(timetable.getId());

        partialUpdatedTimetable
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .titleKr(UPDATED_TITLE_KR)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .contentKr(UPDATED_CONTENT_KR)
            .status(UPDATED_STATUS);

        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimetable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimetable))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTimetable.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTimetable.getTitleKr()).isEqualTo(UPDATED_TITLE_KR);
        assertThat(testTimetable.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTimetable.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTimetable.getContentKr()).isEqualTo(UPDATED_CONTENT_KR);
        assertThat(testTimetable.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();
        timetable.setId(count.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timetableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        int databaseSizeBeforeDelete = timetableRepository.findAll().size();

        // Delete the timetable
        restTimetableMockMvc
            .perform(delete(ENTITY_API_URL_ID, timetable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
