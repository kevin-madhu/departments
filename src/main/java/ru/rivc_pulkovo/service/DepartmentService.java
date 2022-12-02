package ru.rivc_pulkovo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.repository.DepartmentRepository;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;
import ru.rivc_pulkovo.service.dto.DepartmentUpdateDTO;
import ru.rivc_pulkovo.service.mapper.DepartmentMapper;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(departmentRepository.findDepartmentRootId() != null) {
            throw new IllegalArgumentException("The department tree can only have one root node.");
        }

        if(departmentDTO.getDtTill().isBefore(departmentDTO.getDtFrom())) {
            throw new IllegalArgumentException("The department cannot be closed before it's opened.");
        }

        Department department = departmentMapper.toEntity(departmentDTO);
        department.setSystem(false);
        department.setCreationDate(null);
        department.setCorrectionDate(null);

        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    /**
     * Get all the departments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
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
     * Partially update a department.
     *
     * @param departmentUpdateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public DepartmentDTO partialUpdate(DepartmentUpdateDTO departmentUpdateDTO) {
        log.debug("Request to partially update Department : {}", departmentUpdateDTO);

        Department department = departmentRepository.findById(departmentUpdateDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found."));

        if(departmentUpdateDTO.getDtFrom() != null) {
            if(department.getDtTill() != null) {
                if(department.getDtTill().isBefore(departmentUpdateDTO.getDtFrom())) {
                    throw new IllegalArgumentException("The department cannot be closed before it's opened.");
                }
            }

            department.getSubDepartments().forEach(subDepartment -> {
                if(departmentUpdateDTO.getDtFrom() != null) {
                    if(departmentUpdateDTO.getDtFrom().isAfter(subDepartment.getDtFrom())) {
                        throw new IllegalArgumentException("The department cannot be opened after a sub-department has been opened.");
                    }
                }
            });
        }

        Department newDepartment = new Department();
        newDepartment
                .ancestor(department)
                .parent(department.getParent())
                .name(departmentUpdateDTO.getName() != null ? departmentUpdateDTO.getName() : department.getName())
                .dtFrom(departmentUpdateDTO.getDtFrom() != null ? departmentUpdateDTO.getDtFrom(): department.getDtFrom())
                .dtTill(department.getDtTill())
                .sortPriority(department.getSortPriority());

        department.setDtTill(ZonedDateTime.now());
        department.getSubDepartments().forEach(subDepartment -> subDepartment.setParent(newDepartment));

        departmentRepository.saveAll(List.of(newDepartment, department));
        return departmentMapper.toDto(newDepartment);
    }

    /**
     * Partially update a department.
     *
     * @param id the id of the entity.
     */
    public DepartmentDTO close(Long id, ZonedDateTime dtTill) {
        log.debug("Request to close Department : {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found."));

        ZonedDateTime actualDtTill = dtTill != null ? dtTill : ZonedDateTime.now();
        department.getSubDepartments().forEach(subDepartment -> {
            if(subDepartment.getDtTill() != null) {
                if(actualDtTill.isBefore(subDepartment.getDtTill())) {
                    throw new IllegalArgumentException("The department cannot be closed before a sub-department was closed.");
                }
            } else {
                throw new IllegalArgumentException("The department cannot be closed as it has sub-departments that are still active.");
            }
        });

        department.setDtTill(ZonedDateTime.now());
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
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
        return departmentRepository.findAllDepartsOnAParticularDate(date)
                .stream().map(departmentMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
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
