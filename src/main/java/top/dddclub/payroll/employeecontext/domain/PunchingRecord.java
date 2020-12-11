package top.dddclub.payroll.employeecontext.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PunchingRecord {
    private final String employeeId;
    private final LocalDateTime punchingIn;
    private final LocalDateTime punchingOut;
    private final LocalDate today;

    public PunchingRecord(String employeeId, LocalDateTime punchingIn, LocalDateTime punchingOut, LocalDate today) {
        this.employeeId = employeeId;
        this.punchingIn = punchingIn;
        this.punchingOut = punchingOut;
        this.today = today;
    }

    public AttendanceStatus acquireStatus(WorkHour workHour) {
        return null;
    }
}
