package de.informatikwerk.espbroker.domain;


import javax.persistence.*;
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

    @Column(name = "ip")
    private String ip;

    @Column(name = "node_id")
    private Long nodeId;

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

    public Long getNodeId() {
        return nodeId;
    }

    public NodeRegistry nodeId(Long nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
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
            ", nodeId='" + getNodeId() + "'" +
            "}";
    }
}
