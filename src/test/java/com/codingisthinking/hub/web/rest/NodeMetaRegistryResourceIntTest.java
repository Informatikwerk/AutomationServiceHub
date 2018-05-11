package com.codingisthinking.hub.web.rest;

import com.codingisthinking.hub.AutomationServiceHubApp;

import com.codingisthinking.hub.domain.NodeMetaRegistry;
import com.codingisthinking.hub.repository.NodeMetaRegistryRepository;
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
 * Test class for the NodeMetaRegistryResource REST controller.
 *
 * @see NodeMetaRegistryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class NodeMetaRegistryResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_ELEMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_ELEMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_ELEMENTS = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_ELEMENTS = "BBBBBBBBBB";

    @Autowired
    private NodeMetaRegistryRepository nodeMetaRegistryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNodeMetaRegistryMockMvc;

    private NodeMetaRegistry nodeMetaRegistry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeMetaRegistryResource nodeMetaRegistryResource = new NodeMetaRegistryResource(nodeMetaRegistryRepository);
        this.restNodeMetaRegistryMockMvc = MockMvcBuilders.standaloneSetup(nodeMetaRegistryResource)
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
    public static NodeMetaRegistry createEntity(EntityManager em) {
        NodeMetaRegistry nodeMetaRegistry = new NodeMetaRegistry()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .icon_url(DEFAULT_ICON_URL)
            .label(DEFAULT_LABEL)
            .action_elements(DEFAULT_ACTION_ELEMENTS)
            .value_elements(DEFAULT_VALUE_ELEMENTS);
        return nodeMetaRegistry;
    }

    @Before
    public void initTest() {
        nodeMetaRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodeMetaRegistry() throws Exception {
        int databaseSizeBeforeCreate = nodeMetaRegistryRepository.findAll().size();

        // Create the NodeMetaRegistry
        restNodeMetaRegistryMockMvc.perform(post("/api/node-meta-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegistry)))
            .andExpect(status().isCreated());

        // Validate the NodeMetaRegistry in the database
        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        NodeMetaRegistry testNodeMetaRegistry = nodeMetaRegistryList.get(nodeMetaRegistryList.size() - 1);
        assertThat(testNodeMetaRegistry.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNodeMetaRegistry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNodeMetaRegistry.getIcon_url()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testNodeMetaRegistry.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testNodeMetaRegistry.getAction_elements()).isEqualTo(DEFAULT_ACTION_ELEMENTS);
        assertThat(testNodeMetaRegistry.getValue_elements()).isEqualTo(DEFAULT_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void createNodeMetaRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeMetaRegistryRepository.findAll().size();

        // Create the NodeMetaRegistry with an existing ID
        nodeMetaRegistry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMetaRegistryMockMvc.perform(post("/api/node-meta-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegistry)))
            .andExpect(status().isBadRequest());

        // Validate the NodeMetaRegistry in the database
        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nodeMetaRegistryRepository.findAll().size();
        // set the field null
        nodeMetaRegistry.setType(null);

        // Create the NodeMetaRegistry, which fails.

        restNodeMetaRegistryMockMvc.perform(post("/api/node-meta-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegistry)))
            .andExpect(status().isBadRequest());

        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNodeMetaRegistries() throws Exception {
        // Initialize the database
        nodeMetaRegistryRepository.saveAndFlush(nodeMetaRegistry);

        // Get all the nodeMetaRegistryList
        restNodeMetaRegistryMockMvc.perform(get("/api/node-meta-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeMetaRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].icon_url").value(hasItem(DEFAULT_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].action_elements").value(hasItem(DEFAULT_ACTION_ELEMENTS.toString())))
            .andExpect(jsonPath("$.[*].value_elements").value(hasItem(DEFAULT_VALUE_ELEMENTS.toString())));
    }

    @Test
    @Transactional
    public void getNodeMetaRegistry() throws Exception {
        // Initialize the database
        nodeMetaRegistryRepository.saveAndFlush(nodeMetaRegistry);

        // Get the nodeMetaRegistry
        restNodeMetaRegistryMockMvc.perform(get("/api/node-meta-registries/{id}", nodeMetaRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodeMetaRegistry.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.icon_url").value(DEFAULT_ICON_URL.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.action_elements").value(DEFAULT_ACTION_ELEMENTS.toString()))
            .andExpect(jsonPath("$.value_elements").value(DEFAULT_VALUE_ELEMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNodeMetaRegistry() throws Exception {
        // Get the nodeMetaRegistry
        restNodeMetaRegistryMockMvc.perform(get("/api/node-meta-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodeMetaRegistry() throws Exception {
        // Initialize the database
        nodeMetaRegistryRepository.saveAndFlush(nodeMetaRegistry);
        int databaseSizeBeforeUpdate = nodeMetaRegistryRepository.findAll().size();

        // Update the nodeMetaRegistry
        NodeMetaRegistry updatedNodeMetaRegistry = nodeMetaRegistryRepository.findOne(nodeMetaRegistry.getId());
        // Disconnect from session so that the updates on updatedNodeMetaRegistry are not directly saved in db
        em.detach(updatedNodeMetaRegistry);
        updatedNodeMetaRegistry
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .icon_url(UPDATED_ICON_URL)
            .label(UPDATED_LABEL)
            .action_elements(UPDATED_ACTION_ELEMENTS)
            .value_elements(UPDATED_VALUE_ELEMENTS);

        restNodeMetaRegistryMockMvc.perform(put("/api/node-meta-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNodeMetaRegistry)))
            .andExpect(status().isOk());

        // Validate the NodeMetaRegistry in the database
        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeUpdate);
        NodeMetaRegistry testNodeMetaRegistry = nodeMetaRegistryList.get(nodeMetaRegistryList.size() - 1);
        assertThat(testNodeMetaRegistry.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNodeMetaRegistry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNodeMetaRegistry.getIcon_url()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testNodeMetaRegistry.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testNodeMetaRegistry.getAction_elements()).isEqualTo(UPDATED_ACTION_ELEMENTS);
        assertThat(testNodeMetaRegistry.getValue_elements()).isEqualTo(UPDATED_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingNodeMetaRegistry() throws Exception {
        int databaseSizeBeforeUpdate = nodeMetaRegistryRepository.findAll().size();

        // Create the NodeMetaRegistry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNodeMetaRegistryMockMvc.perform(put("/api/node-meta-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegistry)))
            .andExpect(status().isCreated());

        // Validate the NodeMetaRegistry in the database
        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNodeMetaRegistry() throws Exception {
        // Initialize the database
        nodeMetaRegistryRepository.saveAndFlush(nodeMetaRegistry);
        int databaseSizeBeforeDelete = nodeMetaRegistryRepository.findAll().size();

        // Get the nodeMetaRegistry
        restNodeMetaRegistryMockMvc.perform(delete("/api/node-meta-registries/{id}", nodeMetaRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NodeMetaRegistry> nodeMetaRegistryList = nodeMetaRegistryRepository.findAll();
        assertThat(nodeMetaRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeMetaRegistry.class);
        NodeMetaRegistry nodeMetaRegistry1 = new NodeMetaRegistry();
        nodeMetaRegistry1.setId(1L);
        NodeMetaRegistry nodeMetaRegistry2 = new NodeMetaRegistry();
        nodeMetaRegistry2.setId(nodeMetaRegistry1.getId());
        assertThat(nodeMetaRegistry1).isEqualTo(nodeMetaRegistry2);
        nodeMetaRegistry2.setId(2L);
        assertThat(nodeMetaRegistry1).isNotEqualTo(nodeMetaRegistry2);
        nodeMetaRegistry1.setId(null);
        assertThat(nodeMetaRegistry1).isNotEqualTo(nodeMetaRegistry2);
    }
}
