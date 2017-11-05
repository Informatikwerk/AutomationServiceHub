package de.informatikwerk.espbroker.web.rest;

import de.informatikwerk.espbroker.EspBrokerApp;

import de.informatikwerk.espbroker.domain.NodeRegistry;
import de.informatikwerk.espbroker.repository.NodeRegistryRepository;
import de.informatikwerk.espbroker.service.NodeRegistryService;
import de.informatikwerk.espbroker.web.rest.errors.ExceptionTranslator;
import de.informatikwerk.espbroker.service.dto.NodeRegistryCriteria;
import de.informatikwerk.espbroker.service.NodeRegistryQueryService;

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

import static de.informatikwerk.espbroker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NodeRegistryResource REST controller.
 *
 * @see NodeRegistryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EspBrokerApp.class)
public class NodeRegistryResourceIntTest {

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Long DEFAULT_NODE_ID = 1L;
    private static final Long UPDATED_NODE_ID = 2L;

    @Autowired
    private NodeRegistryRepository nodeRegistryRepository;

    @Autowired
    private NodeRegistryService nodeRegistryService;

    @Autowired
    private NodeRegistryQueryService nodeRegistryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNodeRegistryMockMvc;

    private NodeRegistry nodeRegistry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeRegistryResource nodeRegistryResource = new NodeRegistryResource(nodeRegistryService, nodeRegistryQueryService);
        this.restNodeRegistryMockMvc = MockMvcBuilders.standaloneSetup(nodeRegistryResource)
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
    public static NodeRegistry createEntity(EntityManager em) {
        NodeRegistry nodeRegistry = new NodeRegistry()
            .ip(DEFAULT_IP)
            .nodeId(DEFAULT_NODE_ID);
        return nodeRegistry;
    }

    @Before
    public void initTest() {
        nodeRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodeRegistry() throws Exception {
        int databaseSizeBeforeCreate = nodeRegistryRepository.findAll().size();

        // Create the NodeRegistry
        restNodeRegistryMockMvc.perform(post("/api/node-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeRegistry)))
            .andExpect(status().isCreated());

        // Validate the NodeRegistry in the database
        List<NodeRegistry> nodeRegistryList = nodeRegistryRepository.findAll();
        assertThat(nodeRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        NodeRegistry testNodeRegistry = nodeRegistryList.get(nodeRegistryList.size() - 1);
        assertThat(testNodeRegistry.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testNodeRegistry.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
    }

    @Test
    @Transactional
    public void createNodeRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeRegistryRepository.findAll().size();

        // Create the NodeRegistry with an existing ID
        nodeRegistry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeRegistryMockMvc.perform(post("/api/node-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeRegistry)))
            .andExpect(status().isBadRequest());

        // Validate the NodeRegistry in the database
        List<NodeRegistry> nodeRegistryList = nodeRegistryRepository.findAll();
        assertThat(nodeRegistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNodeRegistries() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList
        restNodeRegistryMockMvc.perform(get("/api/node-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getNodeRegistry() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get the nodeRegistry
        restNodeRegistryMockMvc.perform(get("/api/node-registries/{id}", nodeRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodeRegistry.getId().intValue()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where ip equals to DEFAULT_IP
        defaultNodeRegistryShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the nodeRegistryList where ip equals to UPDATED_IP
        defaultNodeRegistryShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByIpIsInShouldWork() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where ip in DEFAULT_IP or UPDATED_IP
        defaultNodeRegistryShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the nodeRegistryList where ip equals to UPDATED_IP
        defaultNodeRegistryShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where ip is not null
        defaultNodeRegistryShouldBeFound("ip.specified=true");

        // Get all the nodeRegistryList where ip is null
        defaultNodeRegistryShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByNodeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where nodeId equals to DEFAULT_NODE_ID
        defaultNodeRegistryShouldBeFound("nodeId.equals=" + DEFAULT_NODE_ID);

        // Get all the nodeRegistryList where nodeId equals to UPDATED_NODE_ID
        defaultNodeRegistryShouldNotBeFound("nodeId.equals=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByNodeIdIsInShouldWork() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where nodeId in DEFAULT_NODE_ID or UPDATED_NODE_ID
        defaultNodeRegistryShouldBeFound("nodeId.in=" + DEFAULT_NODE_ID + "," + UPDATED_NODE_ID);

        // Get all the nodeRegistryList where nodeId equals to UPDATED_NODE_ID
        defaultNodeRegistryShouldNotBeFound("nodeId.in=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByNodeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where nodeId is not null
        defaultNodeRegistryShouldBeFound("nodeId.specified=true");

        // Get all the nodeRegistryList where nodeId is null
        defaultNodeRegistryShouldNotBeFound("nodeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByNodeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where nodeId greater than or equals to DEFAULT_NODE_ID
        defaultNodeRegistryShouldBeFound("nodeId.greaterOrEqualThan=" + DEFAULT_NODE_ID);

        // Get all the nodeRegistryList where nodeId greater than or equals to UPDATED_NODE_ID
        defaultNodeRegistryShouldNotBeFound("nodeId.greaterOrEqualThan=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllNodeRegistriesByNodeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        nodeRegistryRepository.saveAndFlush(nodeRegistry);

        // Get all the nodeRegistryList where nodeId less than or equals to DEFAULT_NODE_ID
        defaultNodeRegistryShouldNotBeFound("nodeId.lessThan=" + DEFAULT_NODE_ID);

        // Get all the nodeRegistryList where nodeId less than or equals to UPDATED_NODE_ID
        defaultNodeRegistryShouldBeFound("nodeId.lessThan=" + UPDATED_NODE_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNodeRegistryShouldBeFound(String filter) throws Exception {
        restNodeRegistryMockMvc.perform(get("/api/node-registries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNodeRegistryShouldNotBeFound(String filter) throws Exception {
        restNodeRegistryMockMvc.perform(get("/api/node-registries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNodeRegistry() throws Exception {
        // Get the nodeRegistry
        restNodeRegistryMockMvc.perform(get("/api/node-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodeRegistry() throws Exception {
        // Initialize the database
        nodeRegistryService.save(nodeRegistry);

        int databaseSizeBeforeUpdate = nodeRegistryRepository.findAll().size();

        // Update the nodeRegistry
        NodeRegistry updatedNodeRegistry = nodeRegistryRepository.findOne(nodeRegistry.getId());
        updatedNodeRegistry
            .ip(UPDATED_IP)
            .nodeId(UPDATED_NODE_ID);

        restNodeRegistryMockMvc.perform(put("/api/node-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNodeRegistry)))
            .andExpect(status().isOk());

        // Validate the NodeRegistry in the database
        List<NodeRegistry> nodeRegistryList = nodeRegistryRepository.findAll();
        assertThat(nodeRegistryList).hasSize(databaseSizeBeforeUpdate);
        NodeRegistry testNodeRegistry = nodeRegistryList.get(nodeRegistryList.size() - 1);
        assertThat(testNodeRegistry.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testNodeRegistry.getNodeId()).isEqualTo(UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNodeRegistry() throws Exception {
        int databaseSizeBeforeUpdate = nodeRegistryRepository.findAll().size();

        // Create the NodeRegistry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNodeRegistryMockMvc.perform(put("/api/node-registries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeRegistry)))
            .andExpect(status().isCreated());

        // Validate the NodeRegistry in the database
        List<NodeRegistry> nodeRegistryList = nodeRegistryRepository.findAll();
        assertThat(nodeRegistryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNodeRegistry() throws Exception {
        // Initialize the database
        nodeRegistryService.save(nodeRegistry);

        int databaseSizeBeforeDelete = nodeRegistryRepository.findAll().size();

        // Get the nodeRegistry
        restNodeRegistryMockMvc.perform(delete("/api/node-registries/{id}", nodeRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NodeRegistry> nodeRegistryList = nodeRegistryRepository.findAll();
        assertThat(nodeRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeRegistry.class);
        NodeRegistry nodeRegistry1 = new NodeRegistry();
        nodeRegistry1.setId(1L);
        NodeRegistry nodeRegistry2 = new NodeRegistry();
        nodeRegistry2.setId(nodeRegistry1.getId());
        assertThat(nodeRegistry1).isEqualTo(nodeRegistry2);
        nodeRegistry2.setId(2L);
        assertThat(nodeRegistry1).isNotEqualTo(nodeRegistry2);
        nodeRegistry1.setId(null);
        assertThat(nodeRegistry1).isNotEqualTo(nodeRegistry2);
    }
}
