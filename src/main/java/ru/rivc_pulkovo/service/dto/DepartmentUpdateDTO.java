package ru.rivc_pulkovo.service.dto;

import javax.validation.constraints.Max;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ru.rivc_pulkovo.domain.Department} entity.
 */
public class DepartmentUpdateDTO {

    private Long id;

    @Max(150)
    private String name;

    private ZonedDateTime dtFrom;

    private int sortPriority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentUpdateDTO)) {
            return false;
        }

        DepartmentUpdateDTO departmentDTO = (DepartmentUpdateDTO) o;
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
        return "DepartmentUpdateDTO{" +
                "id=" + getId() +
                ", dtFrom='" + getDtFrom() + "'" +
                ", sortPriority='" + getSortPriority() + "'" +
                "}";
    }
}
