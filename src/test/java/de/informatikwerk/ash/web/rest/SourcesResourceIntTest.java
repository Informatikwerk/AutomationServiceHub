package de.informatikwerk.ash.web.rest;

import de.informatikwerk.ash.AutomationServiceHubApp;
import de.informatikwerk.ash.domain.Sources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SourcesResource REST controller.
 *
 * @see SourcesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class SourcesResourceIntTest {

    private static final String DEFAULT_SOURCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    @Autowired
    private SourcesRepository sourcesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourcesMockMvc;

    private Sources sources;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourcesResource sourcesResource = new SourcesResource(sourcesRepository);
        this.restSourcesMockMvc = MockMvcBuilders.standaloneSetup(sourcesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(TestUtil.createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sources createEntity(EntityManager em) {
        Sources sources = new Sources()
            .sourceCode(DEFAULT_SOURCE_CODE)
            .fileName(DEFAULT_FILE_NAME);
        return sources;
    }

    @Before
    public void initTest() {
        sources = createEntity(em);
    }

    @Test
    @Transactional
    public void createSources() throws Exception {
        int databaseSizeBeforeCreate = sourcesRepository.findAll().size();

        // Create the Sources
        restSourcesMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sources)))
            .andExpect(status().isCreated());

        // Validate the Sources in the database
        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeCreate + 1);
        Sources testSources = sourcesList.get(sourcesList.size() - 1);
        assertThat(testSources.getSourceCode()).isEqualTo(DEFAULT_SOURCE_CODE);
        assertThat(testSources.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    public void createSourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourcesRepository.findAll().size();

        // Create the Sources with an existing ID
        sources.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourcesMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sources)))
            .andExpect(status().isBadRequest());

        // Validate the Sources in the database
        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSourceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourcesRepository.findAll().size();
        // set the field null
        sources.setSourceCode(null);

        // Create the Sources, which fails.

        restSourcesMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sources)))
            .andExpect(status().isBadRequest());

        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourcesRepository.findAll().size();
        // set the field null
        sources.setFileName(null);

        // Create the Sources, which fails.

        restSourcesMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sources)))
            .andExpect(status().isBadRequest());

        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSources() throws Exception {
        // Initialize the database
        sourcesRepository.saveAndFlush(sources);

        // Get all the sourcesList
        restSourcesMockMvc.perform(get("/api/sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sources.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceCode").value(hasItem(DEFAULT_SOURCE_CODE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSources() throws Exception {
        // Initialize the database
        sourcesRepository.saveAndFlush(sources);

        // Get the sources
        restSourcesMockMvc.perform(get("/api/sources/{id}", sources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sources.getId().intValue()))
            .andExpect(jsonPath("$.sourceCode").value(DEFAULT_SOURCE_CODE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSources() throws Exception {
        // Get the sources
        restSourcesMockMvc.perform(get("/api/sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSources() throws Exception {
        // Initialize the database
        sourcesRepository.saveAndFlush(sources);
        int databaseSizeBeforeUpdate = sourcesRepository.findAll().size();

        // Update the sources
        Sources updatedSources = sourcesRepository.findOne(sources.getId());
        // Disconnect from session so that the updates on updatedSources are not directly saved in db
        em.detach(updatedSources);
        updatedSources
            .sourceCode(UPDATED_SOURCE_CODE)
            .fileName(UPDATED_FILE_NAME);

        restSourcesMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSources)))
            .andExpect(status().isOk());

        // Validate the Sources in the database
        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeUpdate);
        Sources testSources = sourcesList.get(sourcesList.size() - 1);
        assertThat(testSources.getSourceCode()).isEqualTo(UPDATED_SOURCE_CODE);
        assertThat(testSources.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSources() throws Exception {
        int databaseSizeBeforeUpdate = sourcesRepository.findAll().size();

        // Create the Sources

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSourcesMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sources)))
            .andExpect(status().isCreated());

        // Validate the Sources in the database
        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSources() throws Exception {
        // Initialize the database
        sourcesRepository.saveAndFlush(sources);
        int databaseSizeBeforeDelete = sourcesRepository.findAll().size();

        // Get the sources
        restSourcesMockMvc.perform(delete("/api/sources/{id}", sources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sources> sourcesList = sourcesRepository.findAll();
        assertThat(sourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sources.class);
        Sources sources1 = new Sources();
        sources1.setId(1L);
        Sources sources2 = new Sources();
        sources2.setId(sources1.getId());
        assertThat(sources1).isEqualTo(sources2);
        sources2.setId(2L);
        assertThat(sources1).isNotEqualTo(sources2);
        sources1.setId(null);
        assertThat(sources1).isNotEqualTo(sources2);
    }
}
