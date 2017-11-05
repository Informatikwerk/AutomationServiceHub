package de.informatikwerk.espbroker.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the NodeRegistry entity. This class is used in NodeRegistryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /node-registries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NodeRegistryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter ip;

    private LongFilter nodeId;

    public NodeRegistryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIp() {
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public LongFilter getNodeId() {
        return nodeId;
    }

    public void setNodeId(LongFilter nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return "NodeRegistryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ip != null ? "ip=" + ip + ", " : "") +
                (nodeId != null ? "nodeId=" + nodeId + ", " : "") +
            "}";
    }

}
