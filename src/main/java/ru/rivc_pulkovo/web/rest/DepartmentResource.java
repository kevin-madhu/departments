package ru.rivc_pulkovo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rivc_pulkovo.repository.DepartmentRepository;
import ru.rivc_pulkovo.service.DepartmentService;
import ru.rivc_pulkovo.service.dto.DepartmentCreateDTO;
import ru.rivc_pulkovo.service.dto.DepartmentDTO;
import ru.rivc_pulkovo.service.dto.DepartmentUpdateDTO;

/**
 * REST controller for managing {@link ru.rivc_pulkovo.domain.Department}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
    }

    /**
     * {@code POST  /payment-methods} : Create a new department.
     *
     * @param departmentCreateDTO the departmentCreateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentDTO, or with status {@code 400 (Bad Request)} if the department has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO)
            throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentCreateDTO);

        DepartmentDTO result = departmentService.save(departmentCreateDTO);
        return ResponseEntity
                .created(new URI("/api/departments/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PATCH  /payment-methods/:id} : Partial updates given fields of an existing paymentMethod, field will ignore if it is null
     *
     * @param id the id of the paymentMethodDTO to save.
     * @param departmentUpdateDTO the paymentMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethodDTO,
     * or with status {@code 400 (Bad Request)} if the paymentMethodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentMethodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/departments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartmentDTO> partialUpdateDepartment(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody DepartmentUpdateDTO departmentUpdateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Department partially : {}, {}", id, departmentUpdateDTO);
        if (departmentUpdateDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id" + ENTITY_NAME + "idnull");
        }
        if (!Objects.equals(id, departmentUpdateDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID" + ENTITY_NAME + "idinvalid");
        }

        DepartmentDTO result = departmentService.partialUpdate(departmentUpdateDTO);

        return ResponseEntity.ok(result);
    }

    /**
     * {@code GET  /departments} : get all the departments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departments in body.
     */
    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments() {
        log.debug("REST request to get all Departments");
        return departmentService.findAll();
    }

    /**
     * {@code GET  /departments/:id} : get the "id" department.
     *
     * @param id the id of the departmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        return ResponseEntity.of(departmentDTO);
    }

    /**
     * {@code GET  /departments} : get all the departments in a tree.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departments in body.
     */
    @GetMapping("/departments/{id}/tree")
    public List<DepartmentDTO> getDepartmentsHierarchy(@PathVariable Long id, @RequestParam(required = false) ZonedDateTime particularDate) {
        log.debug("REST request to get all Departments in hierarchy.");
        return departmentService.getHierarchy(id, particularDate);
    }

    /**
     * {@code PATCH  /departments/:id/close} : close the "id" department.
     *
     * @param id the id of the department to close.
     * @param dtTill the closing date of the department to close.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentDTO, or with status {@code 404 (Not Found)}.
     */
    @PatchMapping("/departments/{id}/close")
    public ResponseEntity<DepartmentDTO> closeDepartment(@PathVariable Long id, @RequestParam(required = false) ZonedDateTime dtTill) {
        log.debug("REST request to delete Department : {}", id);
        DepartmentDTO departmentDTO = departmentService.close(id, dtTill);
        return ResponseEntity.ok(departmentDTO);
    }

    /**
     * {@code DELETE  /departments/:id} : delete the "id" department.
     *
     * @param id the id of the departmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
