package de.informatikwerk.espbroker.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import de.informatikwerk.espbroker.domain.NodeRegistry;
import de.informatikwerk.espbroker.domain.*; // for static metamodels
import de.informatikwerk.espbroker.repository.NodeRegistryRepository;
import de.informatikwerk.espbroker.service.dto.NodeRegistryCriteria;


/**
 * Service for executing complex queries for NodeRegistry entities in the database.
 * The main input is a {@link NodeRegistryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NodeRegistry} or a {@link Page} of {%link NodeRegistry} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NodeRegistryQueryService extends QueryService<NodeRegistry> {

    private final Logger log = LoggerFactory.getLogger(NodeRegistryQueryService.class);


    private final NodeRegistryRepository nodeRegistryRepository;

    public NodeRegistryQueryService(NodeRegistryRepository nodeRegistryRepository) {
        this.nodeRegistryRepository = nodeRegistryRepository;
    }

    /**
     * Return a {@link List} of {%link NodeRegistry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NodeRegistry> findByCriteria(NodeRegistryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NodeRegistry> specification = createSpecification(criteria);
        return nodeRegistryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link NodeRegistry} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NodeRegistry> findByCriteria(NodeRegistryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NodeRegistry> specification = createSpecification(criteria);
        return nodeRegistryRepository.findAll(specification, page);
    }

    /**
     * Function to convert NodeRegistryCriteria to a {@link Specifications}
     */
    private Specifications<NodeRegistry> createSpecification(NodeRegistryCriteria criteria) {
        Specifications<NodeRegistry> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NodeRegistry_.id));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), NodeRegistry_.ip));
            }
            if (criteria.getNodeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNodeId(), NodeRegistry_.nodeId));
            }
        }
        return specification;
    }

}
