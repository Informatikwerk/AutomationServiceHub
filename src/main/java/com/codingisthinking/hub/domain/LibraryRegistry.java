package com.codingisthinking.hub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LibraryRegistry.
 */
@Entity
@Table(name = "library_registry")
public class LibraryRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "platform", nullable = false)
    private String platform;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "url")
    private String url;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LibraryRegistry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public LibraryRegistry author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public LibraryRegistry description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public LibraryRegistry platform(String platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public LibraryRegistry version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public LibraryRegistry url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
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
        LibraryRegistry libraryRegistry = (LibraryRegistry) o;
        if (libraryRegistry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), libraryRegistry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LibraryRegistry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", author='" + getAuthor() + "'" +
            ", description='" + getDescription() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", version='" + getVersion() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
