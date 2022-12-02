package ru.rivc_pulkovo.service.mapper;

import org.mapstruct.Mapper;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.service.dto.DepartmentCreateDTO;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentCreateMapper extends EntityMapper<DepartmentCreateDTO, Department> {

    //@Mapping(target = "parent.id", source = "parentId")
    Department toEntity(DepartmentDTO d);
}
