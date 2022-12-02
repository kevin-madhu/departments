package ru.rivc_pulkovo.service.mapper;

import org.mapstruct.*;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "ancestorId", source = "ancestor.id")
    DepartmentDTO toDto(Department s);

    @Mapping(target = "parent.id", source = "parentId")
    @Mapping(target = "ancestor.id", source = "ancestorId")
    Department toEntity(DepartmentDTO d);
}
