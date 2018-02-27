package de.informatikwerk.ash.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Gui.
 */
@Entity
@Table(name = "gui")
public class Gui implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Lob
    @Column(name = "js_file")
    private String jsFile;

    @Lob
    @Column(name = "html_file")
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

    public Gui type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJsFile() {
        return jsFile;
    }

    public Gui jsFile(String jsFile) {
        this.jsFile = jsFile;
        return this;
    }

    public void setJsFile(String jsFile) {
        this.jsFile = jsFile;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public Gui htmlFile(String htmlFile) {
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
        Gui gui = (Gui) o;
        if (gui.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gui.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gui{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", jsFile='" + getJsFile() + "'" +
            ", htmlFile='" + getHtmlFile() + "'" +
            "}";
    }
}
