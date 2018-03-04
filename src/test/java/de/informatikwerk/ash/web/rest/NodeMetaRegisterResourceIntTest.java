package de.informatikwerk.ash.web.rest;

import de.informatikwerk.ash.AutomationServiceHubApp;

import de.informatikwerk.ash.domain.NodeMetaRegister;
import de.informatikwerk.ash.repository.NodeMetaRegisterRepository;
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
 * Test class for the NodeMetaRegisterResource REST controller.
 *
 * @see NodeMetaRegisterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutomationServiceHubApp.class)
public class NodeMetaRegisterResourceIntTest {

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
    private NodeMetaRegisterRepository nodeMetaRegisterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNodeMetaRegisterMockMvc;

    private NodeMetaRegister nodeMetaRegister;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeMetaRegisterResource nodeMetaRegisterResource = new NodeMetaRegisterResource(nodeMetaRegisterRepository);
        this.restNodeMetaRegisterMockMvc = MockMvcBuilders.standaloneSetup(nodeMetaRegisterResource)
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
    public static NodeMetaRegister createEntity(EntityManager em) {
        NodeMetaRegister nodeMetaRegister = new NodeMetaRegister()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .iconUrl(DEFAULT_ICON_URL)
            .label(DEFAULT_LABEL)
            .actionElements(DEFAULT_ACTION_ELEMENTS)
            .valueElements(DEFAULT_VALUE_ELEMENTS);
        return nodeMetaRegister;
    }

    @Before
    public void initTest() {
        nodeMetaRegister = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodeMetaRegister() throws Exception {
        int databaseSizeBeforeCreate = nodeMetaRegisterRepository.findAll().size();

        // Create the NodeMetaRegister
        restNodeMetaRegisterMockMvc.perform(post("/api/node-meta-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegister)))
            .andExpect(status().isCreated());

        // Validate the NodeMetaRegister in the database
        List<NodeMetaRegister> nodeMetaRegisterList = nodeMetaRegisterRepository.findAll();
        assertThat(nodeMetaRegisterList).hasSize(databaseSizeBeforeCreate + 1);
        NodeMetaRegister testNodeMetaRegister = nodeMetaRegisterList.get(nodeMetaRegisterList.size() - 1);
        assertThat(testNodeMetaRegister.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNodeMetaRegister.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNodeMetaRegister.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testNodeMetaRegister.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testNodeMetaRegister.getActionElements()).isEqualTo(DEFAULT_ACTION_ELEMENTS);
        assertThat(testNodeMetaRegister.getValueElements()).isEqualTo(DEFAULT_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void createNodeMetaRegisterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeMetaRegisterRepository.findAll().size();

        // Create the NodeMetaRegister with an existing ID
        nodeMetaRegister.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMetaRegisterMockMvc.perform(post("/api/node-meta-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegister)))
            .andExpect(status().isBadRequest());

        // Validate the NodeMetaRegister in the database
        List<NodeMetaRegister> nodeMetaRegisterList = nodeMetaRegisterRepository.findAll();
        assertThat(nodeMetaRegisterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNodeMetaRegisters() throws Exception {
        // Initialize the database
        nodeMetaRegisterRepository.saveAndFlush(nodeMetaRegister);

        // Get all the nodeMetaRegisterList
        restNodeMetaRegisterMockMvc.perform(get("/api/node-meta-registers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeMetaRegister.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].actionElements").value(hasItem(DEFAULT_ACTION_ELEMENTS.toString())))
            .andExpect(jsonPath("$.[*].valueElements").value(hasItem(DEFAULT_VALUE_ELEMENTS.toString())));
    }

    @Test
    @Transactional
    public void getNodeMetaRegister() throws Exception {
        // Initialize the database
        nodeMetaRegisterRepository.saveAndFlush(nodeMetaRegister);

        // Get the nodeMetaRegister
        restNodeMetaRegisterMockMvc.perform(get("/api/node-meta-registers/{id}", nodeMetaRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodeMetaRegister.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.actionElements").value(DEFAULT_ACTION_ELEMENTS.toString()))
            .andExpect(jsonPath("$.valueElements").value(DEFAULT_VALUE_ELEMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNodeMetaRegister() throws Exception {
        // Get the nodeMetaRegister
        restNodeMetaRegisterMockMvc.perform(get("/api/node-meta-registers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodeMetaRegister() throws Exception {
        // Initialize the database
        nodeMetaRegisterRepository.saveAndFlush(nodeMetaRegister);
        int databaseSizeBeforeUpdate = nodeMetaRegisterRepository.findAll().size();

        // Update the nodeMetaRegister
        NodeMetaRegister updatedNodeMetaRegister = nodeMetaRegisterRepository.findOne(nodeMetaRegister.getId());
        // Disconnect from session so that the updates on updatedNodeMetaRegister are not directly saved in db
        em.detach(updatedNodeMetaRegister);
        updatedNodeMetaRegister
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .iconUrl(UPDATED_ICON_URL)
            .label(UPDATED_LABEL)
            .actionElements(UPDATED_ACTION_ELEMENTS)
            .valueElements(UPDATED_VALUE_ELEMENTS);

        restNodeMetaRegisterMockMvc.perform(put("/api/node-meta-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNodeMetaRegister)))
            .andExpect(status().isOk());

        // Validate the NodeMetaRegister in the database
        List<NodeMetaRegister> nodeMetaRegisterList = nodeMetaRegisterRepository.findAll();
        assertThat(nodeMetaRegisterList).hasSize(databaseSizeBeforeUpdate);
        NodeMetaRegister testNodeMetaRegister = nodeMetaRegisterList.get(nodeMetaRegisterList.size() - 1);
        assertThat(testNodeMetaRegister.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNodeMetaRegister.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNodeMetaRegister.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testNodeMetaRegister.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testNodeMetaRegister.getActionElements()).isEqualTo(UPDATED_ACTION_ELEMENTS);
        assertThat(testNodeMetaRegister.getValueElements()).isEqualTo(UPDATED_VALUE_ELEMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingNodeMetaRegister() throws Exception {
        int databaseSizeBeforeUpdate = nodeMetaRegisterRepository.findAll().size();

        // Create the NodeMetaRegister

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNodeMetaRegisterMockMvc.perform(put("/api/node-meta-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeMetaRegister)))
            .andExpect(status().isCreated());

        // Validate the NodeMetaRegister in the database
        List<NodeMetaRegister> nodeMetaRegisterList = nodeMetaRegisterRepository.findAll();
        assertThat(nodeMetaRegisterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNodeMetaRegister() throws Exception {
        // Initialize the database
        nodeMetaRegisterRepository.saveAndFlush(nodeMetaRegister);
        int databaseSizeBeforeDelete = nodeMetaRegisterRepository.findAll().size();

        // Get the nodeMetaRegister
        restNodeMetaRegisterMockMvc.perform(delete("/api/node-meta-registers/{id}", nodeMetaRegister.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NodeMetaRegister> nodeMetaRegisterList = nodeMetaRegisterRepository.findAll();
        assertThat(nodeMetaRegisterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeMetaRegister.class);
        NodeMetaRegister nodeMetaRegister1 = new NodeMetaRegister();
        nodeMetaRegister1.setId(1L);
        NodeMetaRegister nodeMetaRegister2 = new NodeMetaRegister();
        nodeMetaRegister2.setId(nodeMetaRegister1.getId());
        assertThat(nodeMetaRegister1).isEqualTo(nodeMetaRegister2);
        nodeMetaRegister2.setId(2L);
        assertThat(nodeMetaRegister1).isNotEqualTo(nodeMetaRegister2);
        nodeMetaRegister1.setId(null);
        assertThat(nodeMetaRegister1).isNotEqualTo(nodeMetaRegister2);
    }
}
