package ru.rivc_pulkovo.service.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ru.rivc_pulkovo.domain.Department} entity.
 */
public class DepartmentDTO {

    private Long id;

    private Long parentId;

    private Long ancestorId;

    private String name;

    private ZonedDateTime dtFrom;

    private ZonedDateTime dtTill;

    private int sortPriority;

    private boolean isSystem;

    private ZonedDateTime creationDate;

    private ZonedDateTime correctionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(Long ancestorId) {
        this.ancestorId = ancestorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDtFrom() {
        return dtFrom;
    }

    public void setDtFrom(ZonedDateTime dtFrom) {
        this.dtFrom = dtFrom;
    }

    public ZonedDateTime getDtTill() {
        return dtTill;
    }

    public void setDtTill(ZonedDateTime dtTill) {
        this.dtTill = dtTill;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(ZonedDateTime correctionDate) {
        this.correctionDate = correctionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentDTO)) {
            return false;
        }

        DepartmentDTO departmentDTO = (DepartmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "id=" + getId() +
                ", parentId='" + getParentId() + "'" +
                ", dtFrom='" + getDtFrom() + "'" +
                ", dtTill='" + getDtTill() + "'" +
                ", sortPriority='" + getSortPriority() + "'" +
                ", isSystem='" + isSystem() + "'" +
                ", creationDate='" + getCreationDate() + "'" +
                ", correctionDate='" + getCorrectionDate() + "'" +
                "}";
    }
}
