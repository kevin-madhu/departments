package ru.rivc_pulkovo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.repository.DepartmentRepository;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;
import ru.rivc_pulkovo.service.mapper.DepartmentMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Department}.
 */
@Service
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    /**
     * Get one department by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    /**
     * Get department tree as on a particular date.
     *
     * @param date the particular date on which the tree is to be retrieved.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> readTree(ZonedDateTime date) {
        log.debug("Request to get Department tree as on : {}", date);
        List<Department> departments = departmentRepository.findAllDepartsOnAParticularDate(date);

        return departmentMapper.toDto(departments);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

        if(departmentRepository.countDepartmentByParentId(id) > 0) {
            throw new IllegalArgumentException("The department cannot be deleted because it's not a leaf node.");
        } else {
            departmentRepository.deleteById(id);
        }
    }
}
