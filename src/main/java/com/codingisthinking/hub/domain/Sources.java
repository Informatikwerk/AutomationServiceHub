package com.codingisthinking.hub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sources.
 */
@Entity
@Table(name = "sources")
public class Sources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "source_code", nullable = false)
    private String sourceCode;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ManyToOne
    private LibraryRegistry libraryRegistry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public Sources sourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
        return this;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getFileName() {
        return fileName;
    }

    public Sources fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LibraryRegistry getLibraryRegistry() {
        return libraryRegistry;
    }

    public Sources libraryRegistry(LibraryRegistry libraryRegistry) {
        this.libraryRegistry = libraryRegistry;
        return this;
    }

    public void setLibraryRegistry(LibraryRegistry libraryRegistry) {
        this.libraryRegistry = libraryRegistry;
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
        Sources sources = (Sources) o;
        if (sources.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sources.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sources{" +
            "id=" + getId() +
            ", sourceCode='" + getSourceCode() + "'" +
            ", fileName='" + getFileName() + "'" +
            "}";
    }
}
