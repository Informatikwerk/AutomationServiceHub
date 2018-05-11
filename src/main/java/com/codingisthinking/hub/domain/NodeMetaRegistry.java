package com.codingisthinking.hub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NodeMetaRegistry.
 */
@Entity
@Table(name = "node_meta_registry")
public class NodeMetaRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url")
    private String icon_url;

    @Column(name = "jhi_label")
    private String label;

    @Lob
    @Column(name = "action_elements")
    private String action_elements;

    @Lob
    @Column(name = "value_elements")
    private String value_elements;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public NodeMetaRegistry type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public NodeMetaRegistry description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public NodeMetaRegistry icon_url(String icon_url) {
        this.icon_url = icon_url;
        return this;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getLabel() {
        return label;
    }

    public NodeMetaRegistry label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAction_elements() {
        return action_elements;
    }

    public NodeMetaRegistry action_elements(String action_elements) {
        this.action_elements = action_elements;
        return this;
    }

    public void setAction_elements(String action_elements) {
        this.action_elements = action_elements;
    }

    public String getValue_elements() {
        return value_elements;
    }

    public NodeMetaRegistry value_elements(String value_elements) {
        this.value_elements = value_elements;
        return this;
    }

    public void setValue_elements(String value_elements) {
        this.value_elements = value_elements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeMetaRegistry nodeMetaRegistry = (NodeMetaRegistry) o;
        if (nodeMetaRegistry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeMetaRegistry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeMetaRegistry{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", icon_url='" + getIcon_url() + "'" +
            ", label='" + getLabel() + "'" +
            ", action_elements='" + getAction_elements() + "'" +
            ", value_elements='" + getValue_elements() + "'" +
            "}";
    }
}
