package de.informatikwerk.ash.web.rest;


import de.informatikwerk.ash.AutomationServiceHubApp;
import de.informatikwerk.ash.domain.LibraryRegistry;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LibraryRegistryResource REST controller.
 *
 * @see LibraryRegistryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class LibraryRegistryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PLATFORM = "AAAAAAAAAA";
    private static final String UPDATED_PLATFORM = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private LibraryRegistryRepository libraryRegistryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLibraryRegistryMockMvc;

    private LibraryRegistry libraryRegistry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibraryRegistryResource libraryRegistryResource = new LibraryRegistryResource(libraryRegistryRepository);
        this.restLibraryRegistryMockMvc = MockMvcBuilders.standaloneSetup(libraryRegistryResource)
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
    public static LibraryRegistry createEntity(EntityManager em) {
        LibraryRegistry libraryRegistry = new LibraryRegistry()
            .name(DEFAULT_NAME)
            .author(DEFAULT_AUTHOR)
            .description(DEFAULT_DESCRIPTION)
            .platform(DEFAULT_PLATFORM)
            .version(DEFAULT_VERSION)
            .url(DEFAULT_URL)
            .userId(DEFAULT_USER_ID);
        return libraryRegistry;
    }

    @Before
    public void initTest() {
        libraryRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibraryRegistry() throws Exception {
        int databaseSizeBeforeCreate = libraryRegistryRepository.findAll().size();

        // Create the LibraryRegistry
        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isCreated());

        // Validate the LibraryRegistry in the database
        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        LibraryRegistry testLibraryRegistry = libraryRegistryList.get(libraryRegistryList.size() - 1);
        assertThat(testLibraryRegistry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibraryRegistry.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testLibraryRegistry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLibraryRegistry.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testLibraryRegistry.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testLibraryRegistry.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testLibraryRegistry.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createLibraryRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libraryRegistryRepository.findAll().size();

        // Create the LibraryRegistry with an existing ID
        libraryRegistry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        // Validate the LibraryRegistry in the database
        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setName(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setAuthor(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setDescription(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlatformIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setPlatform(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setVersion(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRegistryRepository.findAll().size();
        // set the field null
        libraryRegistry.setUserId(null);

        // Create the LibraryRegistry, which fails.

        restLibraryRegistryMockMvc.perform(post("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isBadRequest());

        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibraryRegistries() throws Exception {
        // Initialize the database
        libraryRegistryRepository.saveAndFlush(libraryRegistry);

        // Get all the libraryRegistryList
        restLibraryRegistryMockMvc.perform(get("/api/library-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libraryRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getLibraryRegistry() throws Exception {
        // Initialize the database
        libraryRegistryRepository.saveAndFlush(libraryRegistry);

        // Get the libraryRegistry
        restLibraryRegistryMockMvc.perform(get("/api/library-registries/{id}", libraryRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libraryRegistry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLibraryRegistry() throws Exception {
        // Get the libraryRegistry
        restLibraryRegistryMockMvc.perform(get("/api/library-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibraryRegistry() throws Exception {
        // Initialize the database
        libraryRegistryRepository.saveAndFlush(libraryRegistry);
        int databaseSizeBeforeUpdate = libraryRegistryRepository.findAll().size();

        // Update the libraryRegistry
        LibraryRegistry updatedLibraryRegistry = libraryRegistryRepository.findOne(libraryRegistry.getId());
        // Disconnect from session so that the updates on updatedLibraryRegistry are not directly saved in db
        em.detach(updatedLibraryRegistry);
        updatedLibraryRegistry
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .platform(UPDATED_PLATFORM)
            .version(UPDATED_VERSION)
            .url(UPDATED_URL)
            .userId(UPDATED_USER_ID);

        restLibraryRegistryMockMvc.perform(put("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibraryRegistry)))
            .andExpect(status().isOk());

        // Validate the LibraryRegistry in the database
        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeUpdate);
        LibraryRegistry testLibraryRegistry = libraryRegistryList.get(libraryRegistryList.size() - 1);
        assertThat(testLibraryRegistry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibraryRegistry.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testLibraryRegistry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLibraryRegistry.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testLibraryRegistry.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testLibraryRegistry.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testLibraryRegistry.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLibraryRegistry() throws Exception {
        int databaseSizeBeforeUpdate = libraryRegistryRepository.findAll().size();

        // Create the LibraryRegistry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibraryRegistryMockMvc.perform(put("/api/library-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryRegistry)))
            .andExpect(status().isCreated());

        // Validate the LibraryRegistry in the database
        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibraryRegistry() throws Exception {
        // Initialize the database
        libraryRegistryRepository.saveAndFlush(libraryRegistry);
        int databaseSizeBeforeDelete = libraryRegistryRepository.findAll().size();

        // Get the libraryRegistry
        restLibraryRegistryMockMvc.perform(delete("/api/library-registries/{id}", libraryRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibraryRegistry> libraryRegistryList = libraryRegistryRepository.findAll();
        assertThat(libraryRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibraryRegistry.class);
        LibraryRegistry libraryRegistry1 = new LibraryRegistry();
        libraryRegistry1.setId(1L);
        LibraryRegistry libraryRegistry2 = new LibraryRegistry();
        libraryRegistry2.setId(libraryRegistry1.getId());
        assertThat(libraryRegistry1).isEqualTo(libraryRegistry2);
        libraryRegistry2.setId(2L);
        assertThat(libraryRegistry1).isNotEqualTo(libraryRegistry2);
        libraryRegistry1.setId(null);
        assertThat(libraryRegistry1).isNotEqualTo(libraryRegistry2);
    }
}
