package de.informatikwerk.ash.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NodeMetaRegister.
 */
@Entity
@Table(name = "node_meta_register")
public class NodeMetaRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "jhi_label")
    private String label;

    @Lob
    @Column(name = "action_elements")
    private String actionElements;

    @Lob
    @Column(name = "value_elements")
    private String valueElements;

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

    public NodeMetaRegister type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public NodeMetaRegister description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public NodeMetaRegister iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLabel() {
        return label;
    }

    public NodeMetaRegister label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getActionElements() {
        return actionElements;
    }

    public NodeMetaRegister actionElements(String actionElements) {
        this.actionElements = actionElements;
        return this;
    }

    public void setActionElements(String actionElements) {
        this.actionElements = actionElements;
    }

    public String getValueElements() {
        return valueElements;
    }

    public NodeMetaRegister valueElements(String valueElements) {
        this.valueElements = valueElements;
        return this;
    }

    public void setValueElements(String valueElements) {
        this.valueElements = valueElements;
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
        NodeMetaRegister nodeMetaRegister = (NodeMetaRegister) o;
        if (nodeMetaRegister.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeMetaRegister.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeMetaRegister{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            ", label='" + getLabel() + "'" +
            ", actionElements='" + getActionElements() + "'" +
            ", valueElements='" + getValueElements() + "'" +
            "}";
    }
}
