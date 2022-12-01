package ru.rivc_pulkovo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rivc_pulkovo.domain.Department;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    int countDepartmentByParentId(Long parentId);

    @Query("SELECT d from Department d where d.dtFrom <= :particularDate and d.dtTill > :partticularDate")
    List<Department> findAllDepartsOnAParticularDate(ZonedDateTime particularDate);

}
