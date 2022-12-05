package ru.rivc_pulkovo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.rivc_pulkovo.config.misc.ApplicationConstants;
import ru.rivc_pulkovo.domain.Department;
import ru.rivc_pulkovo.repository.DepartmentRepository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

@Component
@Profile(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT)
public class H2SeedDataBootstrap implements CommandLineRunner {
    private DepartmentRepository departmentRepository;
    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    @Autowired
    public H2SeedDataBootstrap(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @Override
    public void run(String... args) {

        if(departmentRepository.count() == 0) {
            for (long i=0; i < 1000; ++i) {
                Department department = new Department()
                        .name(String.format("Department %s", i + 1))
                        .dtFrom(ZonedDateTime.now())
                        .sortPriority(new Random().nextInt(1, 5))
                        .creationDate(ZonedDateTime.now());

                Long parentId = i > 20 ? new Random().nextLong(1, i - 1) : null;
                parentId = i % 4 != 0 ? parentId : null;
                Optional<Department> parentDepartmentOptional = parentId != null ? departmentRepository.findById(parentId) : Optional.empty();

                parentDepartmentOptional.ifPresent(department::setParent);

                log.debug("Request to partially update Department : {}", department);
                departmentRepository.save(department);
            }
        }
    }
}
