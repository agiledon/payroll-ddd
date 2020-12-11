package top.dddclub.payroll.employeecontext.domain;

import java.time.LocalTime;

public class WorkHour {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public WorkHour(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
