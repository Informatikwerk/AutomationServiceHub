package de.informatikwerk.ash.web.rest;

import de.informatikwerk.ash.AutomationServiceHubApp;
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
 * Test class for the NodesMetaResource REST controller.
 *
 * @see NodesMetaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class NodesMetaResourceIntTest {

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
    private NodesMetaRepository nodesMetaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNodesMetaMockMvc;

    private NodesMeta nodesMeta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodesMetaResource nodesMetaResource = new NodesMetaResource(nodesMetaRepository);
        this.restNodesMetaMockMvc = MockMvcBuilders.standaloneSetup(nodesMetaResource)
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
    public static NodesMeta createEntity(EntityManager em) {
        NodesMeta nodesMeta = new NodesMeta()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .iconUrl(DEFAULT_ICON_URL)
            .label(DEFAULT_LABEL)
            .actionElements(DEFAULT_ACTION_ELEMENTS)
            .valueElements(DEFAULT_VALUE_ELEMENTS);
        return nodesMeta;
    }

    @Before
    public void initTest() {
        nodesMeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodesMeta() throws Exception {
        int databaseSizeBeforeCreate = nodesMetaRepository.findAll().size();

        // Create the NodesMeta
        restNodesMetaMockMvc.perform(post("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodesMeta)))
            .andExpect(status().isCreated());

        // Validate the NodesMeta in the database
        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeCreate + 1);
        NodesMeta testNodesMeta = nodesMetaList.get(nodesMetaList.size() - 1);
        assertThat(testNodesMeta.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNodesMeta.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNodesMeta.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testNodesMeta.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testNodesMeta.getActionElements()).isEqualTo(DEFAULT_ACTION_ELEMENTS);
        assertThat(testNodesMeta.getValueElements()).isEqualTo(DEFAULT_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void createNodesMetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodesMetaRepository.findAll().size();

        // Create the NodesMeta with an existing ID
        nodesMeta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodesMetaMockMvc.perform(post("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodesMeta)))
            .andExpect(status().isBadRequest());

        // Validate the NodesMeta in the database
        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nodesMetaRepository.findAll().size();
        // set the field null
        nodesMeta.setType(null);

        // Create the NodesMeta, which fails.

        restNodesMetaMockMvc.perform(post("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodesMeta)))
            .andExpect(status().isBadRequest());

        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = nodesMetaRepository.findAll().size();
        // set the field null
        nodesMeta.setDescription(null);

        // Create the NodesMeta, which fails.

        restNodesMetaMockMvc.perform(post("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodesMeta)))
            .andExpect(status().isBadRequest());

        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNodesMetas() throws Exception {
        // Initialize the database
        nodesMetaRepository.saveAndFlush(nodesMeta);

        // Get all the nodesMetaList
        restNodesMetaMockMvc.perform(get("/api/nodes-metas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodesMeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].actionElements").value(hasItem(DEFAULT_ACTION_ELEMENTS.toString())))
            .andExpect(jsonPath("$.[*].valueElements").value(hasItem(DEFAULT_VALUE_ELEMENTS.toString())));
    }

    @Test
    @Transactional
    public void getNodesMeta() throws Exception {
        // Initialize the database
        nodesMetaRepository.saveAndFlush(nodesMeta);

        // Get the nodesMeta
        restNodesMetaMockMvc.perform(get("/api/nodes-metas/{id}", nodesMeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodesMeta.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.actionElements").value(DEFAULT_ACTION_ELEMENTS.toString()))
            .andExpect(jsonPath("$.valueElements").value(DEFAULT_VALUE_ELEMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNodesMeta() throws Exception {
        // Get the nodesMeta
        restNodesMetaMockMvc.perform(get("/api/nodes-metas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodesMeta() throws Exception {
        // Initialize the database
        nodesMetaRepository.saveAndFlush(nodesMeta);
        int databaseSizeBeforeUpdate = nodesMetaRepository.findAll().size();

        // Update the nodesMeta
        NodesMeta updatedNodesMeta = nodesMetaRepository.findOne(nodesMeta.getId());
        // Disconnect from session so that the updates on updatedNodesMeta are not directly saved in db
        em.detach(updatedNodesMeta);
        updatedNodesMeta
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .iconUrl(UPDATED_ICON_URL)
            .label(UPDATED_LABEL)
            .actionElements(UPDATED_ACTION_ELEMENTS)
            .valueElements(UPDATED_VALUE_ELEMENTS);

        restNodesMetaMockMvc.perform(put("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNodesMeta)))
            .andExpect(status().isOk());

        // Validate the NodesMeta in the database
        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeUpdate);
        NodesMeta testNodesMeta = nodesMetaList.get(nodesMetaList.size() - 1);
        assertThat(testNodesMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNodesMeta.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNodesMeta.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testNodesMeta.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testNodesMeta.getActionElements()).isEqualTo(UPDATED_ACTION_ELEMENTS);
        assertThat(testNodesMeta.getValueElements()).isEqualTo(UPDATED_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingNodesMeta() throws Exception {
        int databaseSizeBeforeUpdate = nodesMetaRepository.findAll().size();

        // Create the NodesMeta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNodesMetaMockMvc.perform(put("/api/nodes-metas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodesMeta)))
            .andExpect(status().isCreated());

        // Validate the NodesMeta in the database
        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNodesMeta() throws Exception {
        // Initialize the database
        nodesMetaRepository.saveAndFlush(nodesMeta);
        int databaseSizeBeforeDelete = nodesMetaRepository.findAll().size();

        // Get the nodesMeta
        restNodesMetaMockMvc.perform(delete("/api/nodes-metas/{id}", nodesMeta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NodesMeta> nodesMetaList = nodesMetaRepository.findAll();
        assertThat(nodesMetaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodesMeta.class);
        NodesMeta nodesMeta1 = new NodesMeta();
        nodesMeta1.setId(1L);
        NodesMeta nodesMeta2 = new NodesMeta();
        nodesMeta2.setId(nodesMeta1.getId());
        assertThat(nodesMeta1).isEqualTo(nodesMeta2);
        nodesMeta2.setId(2L);
        assertThat(nodesMeta1).isNotEqualTo(nodesMeta2);
        nodesMeta1.setId(null);
        assertThat(nodesMeta1).isNotEqualTo(nodesMeta2);
    }
}
