package ru.rivc_pulkovo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.rivc_pulkovo.config.misc.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class DepartmentApp {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentApp.class, args);
    }

}
