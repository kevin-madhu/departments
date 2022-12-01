package ru.rivc_pulkovo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Department parent;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "dt_from", nullable = false)
    private ZonedDateTime dtFrom;

    @Column(name = "dt_till")
    private ZonedDateTime dtTill;

    @OneToMany(mappedBy = "parent")
    private List<Department> subDepartments;

    @NotNull
    @Column(name = "sort_priority")
    private int sortPriority;

    @NotNull
    @Column(name = "is_system")
    private boolean isSystem;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "correction_date")
    private ZonedDateTime correctionDate;

    public Long getId() {
        return this.id;
    }

    public Department id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getParent() {
        return parent;
    }

    public Department parent(Department parent) {
        this.setParent(parent);
        return this;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDtFrom() {
        return dtFrom;
    }

    public Department dtFrom(ZonedDateTime dtFrom) {
        this.dtFrom = dtFrom;
        return this;
    }

    public void setDtFrom(ZonedDateTime dtFrom) {
        this.dtFrom = dtFrom;
    }

    public ZonedDateTime getDtTill() {
        return dtTill;
    }

    public Department dtTill(ZonedDateTime dtTill) {
        this.dtTill = dtTill;
        return this;
    }

    public void setDtTill(ZonedDateTime dtTill) {
        this.dtTill = dtTill;
    }

    public List<Department> getSubDepartments() {
        return subDepartments;
    }

    public void setSubDepartments(List<Department> subDivisions) {
        this.subDepartments = subDivisions;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public Department sortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
        return this;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public Department system(boolean system) {
        isSystem = system;
        return this;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Department creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCorrectionDate() {
        return correctionDate;
    }

    public Department correctionDate(ZonedDateTime correctionDate) {
        this.correctionDate = correctionDate;
        return this;
    }

    public void setCorrectionDate(ZonedDateTime correctionDate) {
        this.correctionDate = correctionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + getId() +
                ", parentId='" + getParent().id + "'" +
                ", dtFrom='" + getDtFrom() + "'" +
                ", dtTill='" + getDtTill() + "'" +
                ", sortPriority='" + getSortPriority() + "'" +
                ", isSystem='" + isSystem() + "'" +
                ", creationDate='" + getCreationDate() + "'" +
                ", correctionDate='" + getCorrectionDate() + "'" +
                "}";
    }
}
