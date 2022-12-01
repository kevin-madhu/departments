package ru.rivc_pulkovo.service.builder;

import ru.rivc_pulkovo.service.dto.DepartmentDTO;
import java.time.ZonedDateTime;

/**
 * A Builder class for the {@link ru.rivc_pulkovo.service.dto.DepartmentDTO} entity.
 */
public class DepartmentDTOBuilder {

    private Long id;

    private Long parentId;

    private String name;

    private ZonedDateTime dtFrom;

    private ZonedDateTime dtTill;

    private int sortPriority;

    private boolean isSystem;

    private ZonedDateTime creationDate;

    private ZonedDateTime correctionDate;

    public DepartmentDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public DepartmentDTOBuilder setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public DepartmentDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentDTOBuilder setDtFrom(ZonedDateTime dtFrom) {
        this.dtFrom = dtFrom;
        return this;
    }

    public DepartmentDTOBuilder setDtTill(ZonedDateTime dtTill) {
        this.dtTill = dtTill;
        return this;
    }

    public DepartmentDTOBuilder setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
        return this;
    }

    public DepartmentDTOBuilder setSystem(boolean system) {
        isSystem = system;
        return this;
    }

    public DepartmentDTOBuilder setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public DepartmentDTOBuilder setCorrectionDate(ZonedDateTime correctionDate) {
        this.correctionDate = correctionDate;
        return this;
    }

    public DepartmentDTO build() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(this.id);
        departmentDTO.setParentId(this.parentId);
        departmentDTO.setName(this.name);
        departmentDTO.setDtFrom(this.dtFrom);
        departmentDTO.setDtTill(this.dtTill);
        departmentDTO.setSortPriority(this.sortPriority);
        departmentDTO.setSystem(this.isSystem);
        departmentDTO.setCorrectionDate(this.creationDate);
        departmentDTO.setCorrectionDate(this.correctionDate);

        return departmentDTO;
    }
}
