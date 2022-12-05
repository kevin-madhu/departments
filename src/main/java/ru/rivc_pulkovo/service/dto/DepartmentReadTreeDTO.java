package ru.rivc_pulkovo.service.dto;

import java.time.ZonedDateTime;

public class DepartmentReadTreeDTO {
    private ZonedDateTime particularDate;
    public ZonedDateTime getParticularDate() {
        return particularDate;
    }

    public void setParticularDate(ZonedDateTime particularDate) {
        this.particularDate = particularDate;
    }
}
