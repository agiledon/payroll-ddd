package top.dddclub.payroll.employeecontext.port;

import java.time.LocalDate;

public interface LeaveRepository {
    boolean leaveFor(String employeeId, LocalDate day);
}