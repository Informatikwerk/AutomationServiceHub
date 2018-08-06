package de.informatikwerk.ash.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GuiRegister.
 */
@Entity
@Table(name = "gui_register")
public class GuiRegister implements Serializable {

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
    private String js_file;

    @NotNull
    @Lob
    @Column(name = "html_file", nullable = false)
    private String html_file;

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

    public GuiRegister type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJs_file() {
        return js_file;
    }

    public GuiRegister js_file(String js_file) {
        this.js_file = js_file;
        return this;
    }

    public void setJs_file(String js_file) {
        this.js_file = js_file;
    }

    public String getHtml_file() {
        return html_file;
    }

    public GuiRegister html_file(String html_file) {
        this.html_file = html_file;
        return this;
    }

    public void setHtml_file(String html_file) {
        this.html_file = html_file;
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
        GuiRegister guiRegister = (GuiRegister) o;
        if (guiRegister.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), guiRegister.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GuiRegister{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", js_file='" + getJs_file() + "'" +
            ", html_file='" + getHtml_file() + "'" +
            "}";
    }
}
