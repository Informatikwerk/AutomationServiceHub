package com.codingisthinking.hub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Nodes.
 */
@Entity
@Table(name = "nodes")
public class Nodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @NotNull
    @Column(name = "realm_key", nullable = false)
    private String realmKey;

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

    public Nodes ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRealmKey() {
        return realmKey;
    }

    public Nodes realmKey(String realmKey) {
        this.realmKey = realmKey;
        return this;
    }

    public void setRealmKey(String realmKey) {
        this.realmKey = realmKey;
    }

    public String getType() {
        return type;
    }

    public Nodes type(String type) {
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
        Nodes nodes = (Nodes) o;
        if (nodes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Nodes{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", realmKey='" + getRealmKey() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
