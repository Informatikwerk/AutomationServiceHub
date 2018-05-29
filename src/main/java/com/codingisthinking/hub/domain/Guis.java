package com.codingisthinking.hub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Guis.
 */
@Entity
@Table(name = "guis")
public class Guis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Lob
    @Column(name = "js_file", nullable = false)
    private String jsFile;

    @NotNull
    @Lob
    @Column(name = "html_file", nullable = false)
    private String htmlFile;

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

    public Guis type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJsFile() {
        return jsFile;
    }

    public Guis jsFile(String jsFile) {
        this.jsFile = jsFile;
        return this;
    }

    public void setJsFile(String jsFile) {
        this.jsFile = jsFile;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public Guis htmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
        return this;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
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
        Guis guis = (Guis) o;
        if (guis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), guis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Guis{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", jsFile='" + getJsFile() + "'" +
            ", htmlFile='" + getHtmlFile() + "'" +
            "}";
    }
}
