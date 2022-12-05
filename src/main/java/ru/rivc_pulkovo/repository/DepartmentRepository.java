package ru.rivc_pulkovo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rivc_pulkovo.domain.Department;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    int countDepartmentByParentId(Long parentId);

    @Query("SELECT d.id from Department d where d.parent.id is null")
    Long findDepartmentRootId();

    @Query(value = """
        WITH RECURSIVE Subdepartments(id, parent_id, ancestor_id, name, dt_from, dt_till, sort_priority, is_system, creation_date, correction_date) AS (
            SELECT id, parent_id, ancestor_id, name, dt_from, dt_till, sort_priority, is_system, creation_date, correction_date
             FROM Department WHERE id = :id
                UNION
            SELECT d.id, d.parent_id, d.ancestor_id, d.name, d.dt_from, d.dt_till, d.sort_priority, d.is_system, d.creation_date, d.correction_date
             FROM Subdepartments s
            INNER JOIN Department d ON s.id = d.parent_id
        )
        SELECT * FROM Subdepartments""", nativeQuery = true
    )
    List<Department> getAllByHierarchy(@Param("id") Long id);

    @Query(value = """
        WITH RECURSIVE Subdepartments(id, parent_id, ancestor_id, name, dt_from, dt_till, sort_priority, is_system, creation_date, correction_date) AS (
            SELECT id, parent_id, ancestor_id, name, dt_from, dt_till, sort_priority, is_system, creation_date, correction_date
             FROM Department WHERE id = :id
                UNION
            SELECT d.id, d.parent_id, d.ancestor_id, d.name, d.dt_from, d.dt_till, d.sort_priority, d.is_system, d.creation_date, d.correction_date
             FROM Subdepartments s
            INNER JOIN Department d ON s.id = d.parent_id
        )
        SELECT * FROM Subdepartments s WHERE(s.dt_from <= :particularDate AND (s.dt_till IS null OR (s.dt_till > :particularDate)))""", nativeQuery = true
    )
    List<Department> getAllByHierarchyOnAParticularDate(@Param("id") Long id,
                                       @Param("particularDate") ZonedDateTime particularDate);
}
