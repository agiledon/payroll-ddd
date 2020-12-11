package top.dddclub.payroll.employeecontext.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkHour {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private static final int TOLERANCE_MINUTES = 15;

    public WorkHour(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isLate(LocalDateTime punchingIn) {
        LocalTime latestArrivedTime = startTime.plusMinutes(TOLERANCE_MINUTES);
        return punchingIn.toLocalTime().isAfter(latestArrivedTime);
    }
}
