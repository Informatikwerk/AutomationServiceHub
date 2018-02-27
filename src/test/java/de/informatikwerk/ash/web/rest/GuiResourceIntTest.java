package de.informatikwerk.ash.web.rest;

import de.informatikwerk.ash.AutomationServiceHubApp;

import de.informatikwerk.ash.domain.Gui;
import de.informatikwerk.ash.repository.GuiRepository;
import de.informatikwerk.ash.web.rest.errors.ExceptionTranslator;

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

import static de.informatikwerk.ash.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GuiResource REST controller.
 *
 * @see GuiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class GuiResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JS_FILE = "AAAAAAAAAA";
    private static final String UPDATED_JS_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_HTML_FILE = "AAAAAAAAAA";
    private static final String UPDATED_HTML_FILE = "BBBBBBBBBB";

    @Autowired
    private GuiRepository guiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGuiMockMvc;

    private Gui gui;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuiResource guiResource = new GuiResource(guiRepository);
        this.restGuiMockMvc = MockMvcBuilders.standaloneSetup(guiResource)
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
    public static Gui createEntity(EntityManager em) {
        Gui gui = new Gui()
            .type(DEFAULT_TYPE)
            .jsFile(DEFAULT_JS_FILE)
            .htmlFile(DEFAULT_HTML_FILE);
        return gui;
    }

    @Before
    public void initTest() {
        gui = createEntity(em);
    }

    @Test
    @Transactional
    public void createGui() throws Exception {
        int databaseSizeBeforeCreate = guiRepository.findAll().size();

        // Create the Gui
        restGuiMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gui)))
            .andExpect(status().isCreated());

        // Validate the Gui in the database
        List<Gui> guiList = guiRepository.findAll();
        assertThat(guiList).hasSize(databaseSizeBeforeCreate + 1);
        Gui testGui = guiList.get(guiList.size() - 1);
        assertThat(testGui.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGui.getJsFile()).isEqualTo(DEFAULT_JS_FILE);
        assertThat(testGui.getHtmlFile()).isEqualTo(DEFAULT_HTML_FILE);
    }

    @Test
    @Transactional
    public void createGuiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guiRepository.findAll().size();

        // Create the Gui with an existing ID
        gui.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuiMockMvc.perform(post("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gui)))
            .andExpect(status().isBadRequest());

        // Validate the Gui in the database
        List<Gui> guiList = guiRepository.findAll();
        assertThat(guiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGuis() throws Exception {
        // Initialize the database
        guiRepository.saveAndFlush(gui);

        // Get all the guiList
        restGuiMockMvc.perform(get("/api/guis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gui.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].jsFile").value(hasItem(DEFAULT_JS_FILE.toString())))
            .andExpect(jsonPath("$.[*].htmlFile").value(hasItem(DEFAULT_HTML_FILE.toString())));
    }

    @Test
    @Transactional
    public void getGui() throws Exception {
        // Initialize the database
        guiRepository.saveAndFlush(gui);

        // Get the gui
        restGuiMockMvc.perform(get("/api/guis/{id}", gui.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gui.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.jsFile").value(DEFAULT_JS_FILE.toString()))
            .andExpect(jsonPath("$.htmlFile").value(DEFAULT_HTML_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGui() throws Exception {
        // Get the gui
        restGuiMockMvc.perform(get("/api/guis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGui() throws Exception {
        // Initialize the database
        guiRepository.saveAndFlush(gui);
        int databaseSizeBeforeUpdate = guiRepository.findAll().size();

        // Update the gui
        Gui updatedGui = guiRepository.findOne(gui.getId());
        // Disconnect from session so that the updates on updatedGui are not directly saved in db
        em.detach(updatedGui);
        updatedGui
            .type(UPDATED_TYPE)
            .jsFile(UPDATED_JS_FILE)
            .htmlFile(UPDATED_HTML_FILE);

        restGuiMockMvc.perform(put("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGui)))
            .andExpect(status().isOk());

        // Validate the Gui in the database
        List<Gui> guiList = guiRepository.findAll();
        assertThat(guiList).hasSize(databaseSizeBeforeUpdate);
        Gui testGui = guiList.get(guiList.size() - 1);
        assertThat(testGui.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGui.getJsFile()).isEqualTo(UPDATED_JS_FILE);
        assertThat(testGui.getHtmlFile()).isEqualTo(UPDATED_HTML_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingGui() throws Exception {
        int databaseSizeBeforeUpdate = guiRepository.findAll().size();

        // Create the Gui

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGuiMockMvc.perform(put("/api/guis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gui)))
            .andExpect(status().isCreated());

        // Validate the Gui in the database
        List<Gui> guiList = guiRepository.findAll();
        assertThat(guiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGui() throws Exception {
        // Initialize the database
        guiRepository.saveAndFlush(gui);
        int databaseSizeBeforeDelete = guiRepository.findAll().size();

        // Get the gui
        restGuiMockMvc.perform(delete("/api/guis/{id}", gui.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gui> guiList = guiRepository.findAll();
        assertThat(guiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gui.class);
        Gui gui1 = new Gui();
        gui1.setId(1L);
        Gui gui2 = new Gui();
        gui2.setId(gui1.getId());
        assertThat(gui1).isEqualTo(gui2);
        gui2.setId(2L);
        assertThat(gui1).isNotEqualTo(gui2);
        gui1.setId(null);
        assertThat(gui1).isNotEqualTo(gui2);
    }
}
