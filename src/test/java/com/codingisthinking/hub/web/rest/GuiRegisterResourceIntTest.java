package com.codingisthinking.hub.web.rest;

import com.codingisthinking.hub.AutomationServiceHubApp;

import com.codingisthinking.hub.domain.GuiRegister;
import com.codingisthinking.hub.repository.GuiRegisterRepository;
import com.codingisthinking.hub.web.rest.errors.ExceptionTranslator;

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

import static com.codingisthinking.hub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GuiRegisterResource REST controller.
 *
 * @see GuiRegisterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class GuiRegisterResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JS_FILE = "AAAAAAAAAA";
    private static final String UPDATED_JS_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_HTML_FILE = "AAAAAAAAAA";
    private static final String UPDATED_HTML_FILE = "BBBBBBBBBB";

    @Autowired
    private GuiRegisterRepository guiRegisterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGuiRegisterMockMvc;

    private GuiRegister guiRegister;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuiRegisterResource guiRegisterResource = new GuiRegisterResource(guiRegisterRepository);
        this.restGuiRegisterMockMvc = MockMvcBuilders.standaloneSetup(guiRegisterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuiRegister createEntity(EntityManager em) {
        GuiRegister guiRegister = new GuiRegister()
            .type(DEFAULT_TYPE)
            .js_file(DEFAULT_JS_FILE)
            .html_file(DEFAULT_HTML_FILE);
        return guiRegister;
    }

    @Before
    public void initTest() {
        guiRegister = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuiRegister() throws Exception {
        int databaseSizeBeforeCreate = guiRegisterRepository.findAll().size();

        // Create the GuiRegister
        restGuiRegisterMockMvc.perform(post("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isCreated());

        // Validate the GuiRegister in the database
        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeCreate + 1);
        GuiRegister testGuiRegister = guiRegisterList.get(guiRegisterList.size() - 1);
        assertThat(testGuiRegister.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGuiRegister.getJs_file()).isEqualTo(DEFAULT_JS_FILE);
        assertThat(testGuiRegister.getHtml_file()).isEqualTo(DEFAULT_HTML_FILE);
    }

    @Test
    @Transactional
    public void createGuiRegisterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guiRegisterRepository.findAll().size();

        // Create the GuiRegister with an existing ID
        guiRegister.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuiRegisterMockMvc.perform(post("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isBadRequest());

        // Validate the GuiRegister in the database
        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiRegisterRepository.findAll().size();
        // set the field null
        guiRegister.setType(null);

        // Create the GuiRegister, which fails.

        restGuiRegisterMockMvc.perform(post("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isBadRequest());

        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJs_fileIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiRegisterRepository.findAll().size();
        // set the field null
        guiRegister.setJs_file(null);

        // Create the GuiRegister, which fails.

        restGuiRegisterMockMvc.perform(post("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isBadRequest());

        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHtml_fileIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiRegisterRepository.findAll().size();
        // set the field null
        guiRegister.setHtml_file(null);

        // Create the GuiRegister, which fails.

        restGuiRegisterMockMvc.perform(post("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isBadRequest());

        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuiRegisters() throws Exception {
        // Initialize the database
        guiRegisterRepository.saveAndFlush(guiRegister);

        // Get all the guiRegisterList
        restGuiRegisterMockMvc.perform(get("/api/gui-registers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guiRegister.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].js_file").value(hasItem(DEFAULT_JS_FILE.toString())))
            .andExpect(jsonPath("$.[*].html_file").value(hasItem(DEFAULT_HTML_FILE.toString())));
    }

    @Test
    @Transactional
    public void getGuiRegister() throws Exception {
        // Initialize the database
        guiRegisterRepository.saveAndFlush(guiRegister);

        // Get the guiRegister
        restGuiRegisterMockMvc.perform(get("/api/gui-registers/{id}", guiRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(guiRegister.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.js_file").value(DEFAULT_JS_FILE.toString()))
            .andExpect(jsonPath("$.html_file").value(DEFAULT_HTML_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGuiRegister() throws Exception {
        // Get the guiRegister
        restGuiRegisterMockMvc.perform(get("/api/gui-registers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuiRegister() throws Exception {
        // Initialize the database
        guiRegisterRepository.saveAndFlush(guiRegister);
        int databaseSizeBeforeUpdate = guiRegisterRepository.findAll().size();

        // Update the guiRegister
        GuiRegister updatedGuiRegister = guiRegisterRepository.findOne(guiRegister.getId());
        // Disconnect from session so that the updates on updatedGuiRegister are not directly saved in db
        em.detach(updatedGuiRegister);
        updatedGuiRegister
            .type(UPDATED_TYPE)
            .js_file(UPDATED_JS_FILE)
            .html_file(UPDATED_HTML_FILE);

        restGuiRegisterMockMvc.perform(put("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGuiRegister)))
            .andExpect(status().isOk());

        // Validate the GuiRegister in the database
        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeUpdate);
        GuiRegister testGuiRegister = guiRegisterList.get(guiRegisterList.size() - 1);
        assertThat(testGuiRegister.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGuiRegister.getJs_file()).isEqualTo(UPDATED_JS_FILE);
        assertThat(testGuiRegister.getHtml_file()).isEqualTo(UPDATED_HTML_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingGuiRegister() throws Exception {
        int databaseSizeBeforeUpdate = guiRegisterRepository.findAll().size();

        // Create the GuiRegister

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGuiRegisterMockMvc.perform(put("/api/gui-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guiRegister)))
            .andExpect(status().isCreated());

        // Validate the GuiRegister in the database
        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGuiRegister() throws Exception {
        // Initialize the database
        guiRegisterRepository.saveAndFlush(guiRegister);
        int databaseSizeBeforeDelete = guiRegisterRepository.findAll().size();

        // Get the guiRegister
        restGuiRegisterMockMvc.perform(delete("/api/gui-registers/{id}", guiRegister.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GuiRegister> guiRegisterList = guiRegisterRepository.findAll();
        assertThat(guiRegisterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuiRegister.class);
        GuiRegister guiRegister1 = new GuiRegister();
        guiRegister1.setId(1L);
        GuiRegister guiRegister2 = new GuiRegister();
        guiRegister2.setId(guiRegister1.getId());
        assertThat(guiRegister1).isEqualTo(guiRegister2);
        guiRegister2.setId(2L);
        assertThat(guiRegister1).isNotEqualTo(guiRegister2);
        guiRegister1.setId(null);
        assertThat(guiRegister1).isNotEqualTo(guiRegister2);
    }
}
