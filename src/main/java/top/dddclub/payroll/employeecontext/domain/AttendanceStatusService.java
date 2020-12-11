package top.dddclub.payroll.employeecontext.domain;

import top.dddclub.payroll.employeecontext.port.LeaveRepository;

import java.time.LocalDate;

public class AttendanceStatusService {
    private LeaveRepository leaveRepo;

    public AttendanceStatusService(LeaveRepository leaveRepo) {
        this.leaveRepo = leaveRepo;
    }

    public AttendanceStatus acquireStatus(String employeeId, LocalDate date) {
        if (leaveRepo.leaveFor(employeeId, date)) {
            return AttendanceStatus.Leave;
        }
        return null;
    }
}