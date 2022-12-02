package ru.rivc_pulkovo.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

/**
 * A DTO for the {@link ru.rivc_pulkovo.domain.Department} entity.
 */
public class DepartmentCreateDTO {
    private Long parentId;
    @NotNull
    @Size(max = 150)
    private String name;

    private ZonedDateTime dtFrom;

    private ZonedDateTime dtTill;

    @NotNull
    private int sortPriority;

    @NotNull
    private boolean isSystem;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    //TODO - Write an equals and hashCode method (Low Priority)

    @Override
    public String toString() {
        return "DepartmentCreateDTO{" +
                ", parentId='" + getParentId() + "'" +
                ", dtFrom='" + getDtFrom() + "'" +
                ", dtTill='" + getDtTill() + "'" +
                ", sortPriority='" + getSortPriority() + "'" +
                ", isSystem='" + isSystem() + "'" +
                "}";
    }
}
