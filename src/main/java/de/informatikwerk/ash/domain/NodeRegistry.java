package de.informatikwerk.ash.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NodeRegistry.
 */
@Entity
@Table(name = "node_registry")
public class NodeRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @NotNull
    @Column(name = "reaml_key", nullable = false)
    private String reaml_key;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public NodeRegistry ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReaml_key() {
        return reaml_key;
    }

    public NodeRegistry reaml_key(String reaml_key) {
        this.reaml_key = reaml_key;
        return this;
    }

    public void setReaml_key(String reaml_key) {
        this.reaml_key = reaml_key;
    }

    public String getType() {
        return type;
    }

    public NodeRegistry type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
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
        NodeRegistry nodeRegistry = (NodeRegistry) o;
        if (nodeRegistry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeRegistry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeRegistry{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", reaml_key='" + getReaml_key() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
