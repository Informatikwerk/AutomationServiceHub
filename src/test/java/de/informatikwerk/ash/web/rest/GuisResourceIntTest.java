package de.informatikwerk.ash.web.rest;

import de.informatikwerk.ash.AutomationServiceHubApp;
import de.informatikwerk.ash.domain.Guis;
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
 * Test class for the GuisResource REST controller.
 *
 * @see GuisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class GuisResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JS_FILE = "AAAAAAAAAA";
    private static final String UPDATED_JS_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_HTML_FILE = "AAAAAAAAAA";
    private static final String UPDATED_HTML_FILE = "BBBBBBBBBB";

    @Autowired
    private GuisRepository guisRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGuisMockMvc;

    private Guis guis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuisResource guisResource = new GuisResource(guisRepository);
        this.restGuisMockMvc = MockMvcBuilders.standaloneSetup(guisResource)
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
    public static Guis createEntity(EntityManager em) {
        Guis guis = new Guis()
            .type(DEFAULT_TYPE)
            .jsFile(DEFAULT_JS_FILE)
            .htmlFile(DEFAULT_HTML_FILE);
        return guis;
    }

    @Before
    public void initTest() {
        guis = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuis() throws Exception {
        int databaseSizeBeforeCreate = guisRepository.findAll().size();

        // Create the Guis
        restGuisMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isCreated());

        // Validate the Guis in the database
        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeCreate + 1);
        Guis testGuis = guisList.get(guisList.size() - 1);
        assertThat(testGuis.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGuis.getJsFile()).isEqualTo(DEFAULT_JS_FILE);
        assertThat(testGuis.getHtmlFile()).isEqualTo(DEFAULT_HTML_FILE);
    }

    @Test
    @Transactional
    public void createGuisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guisRepository.findAll().size();

        // Create the Guis with an existing ID
        guis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuisMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isBadRequest());

        // Validate the Guis in the database
        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = guisRepository.findAll().size();
        // set the field null
        guis.setType(null);

        // Create the Guis, which fails.

        restGuisMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isBadRequest());

        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJsFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = guisRepository.findAll().size();
        // set the field null
        guis.setJsFile(null);

        // Create the Guis, which fails.

        restGuisMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isBadRequest());

        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHtmlFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = guisRepository.findAll().size();
        // set the field null
        guis.setHtmlFile(null);

        // Create the Guis, which fails.

        restGuisMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isBadRequest());

        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuis() throws Exception {
        // Initialize the database
        guisRepository.saveAndFlush(guis);

        // Get all the guisList
        restGuisMockMvc.perform(get("/api/guis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guis.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].jsFile").value(hasItem(DEFAULT_JS_FILE.toString())))
            .andExpect(jsonPath("$.[*].htmlFile").value(hasItem(DEFAULT_HTML_FILE.toString())));
    }

    @Test
    @Transactional
    public void getGuis() throws Exception {
        // Initialize the database
        guisRepository.saveAndFlush(guis);

        // Get the guis
        restGuisMockMvc.perform(get("/api/guis/{id}", guis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(guis.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.jsFile").value(DEFAULT_JS_FILE.toString()))
            .andExpect(jsonPath("$.htmlFile").value(DEFAULT_HTML_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGuis() throws Exception {
        // Get the guis
        restGuisMockMvc.perform(get("/api/guis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuis() throws Exception {
        // Initialize the database
        guisRepository.saveAndFlush(guis);
        int databaseSizeBeforeUpdate = guisRepository.findAll().size();

        // Update the guis
        Guis updatedGuis = guisRepository.findOne(guis.getId());
        // Disconnect from session so that the updates on updatedGuis are not directly saved in db
        em.detach(updatedGuis);
        updatedGuis
            .type(UPDATED_TYPE)
            .jsFile(UPDATED_JS_FILE)
            .htmlFile(UPDATED_HTML_FILE);

        restGuisMockMvc.perform(put("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGuis)))
            .andExpect(status().isOk());

        // Validate the Guis in the database
        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeUpdate);
        Guis testGuis = guisList.get(guisList.size() - 1);
        assertThat(testGuis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGuis.getJsFile()).isEqualTo(UPDATED_JS_FILE);
        assertThat(testGuis.getHtmlFile()).isEqualTo(UPDATED_HTML_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingGuis() throws Exception {
        int databaseSizeBeforeUpdate = guisRepository.findAll().size();

        // Create the Guis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGuisMockMvc.perform(put("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guis)))
            .andExpect(status().isCreated());

        // Validate the Guis in the database
        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGuis() throws Exception {
        // Initialize the database
        guisRepository.saveAndFlush(guis);
        int databaseSizeBeforeDelete = guisRepository.findAll().size();

        // Get the guis
        restGuisMockMvc.perform(delete("/api/guis/{id}", guis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Guis> guisList = guisRepository.findAll();
        assertThat(guisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guis.class);
        Guis guis1 = new Guis();
        guis1.setId(1L);
        Guis guis2 = new Guis();
        guis2.setId(guis1.getId());
        assertThat(guis1).isEqualTo(guis2);
        guis2.setId(2L);
        assertThat(guis1).isNotEqualTo(guis2);
        guis1.setId(null);
        assertThat(guis1).isNotEqualTo(guis2);
    }
}
