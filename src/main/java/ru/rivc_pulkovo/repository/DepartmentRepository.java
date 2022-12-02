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

    //TODO - Query needs to be fixed!!!
    @Query(value = """
        WITH RECURSIVE subdepartments AS (
        SELECT
            *
        FROM
            Department d
        WHERE
            id = :parentId
        UNION
            SELECT
                *
            FROM
                Department d
            INNER JOIN subdepartments s ON d.parent_id = d.id
        ) SELECT * FROM subdepartments s WHERE(s.dt_from <= :particularDate AND s.dt_till > :particularDate)""", nativeQuery = true)
    List<Department> getAllByHierarchy(@Param("parentId") Long parentId,
                                       @Param("particularDate") ZonedDateTime particularDate);
}
