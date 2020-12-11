package top.dddclub.payroll.employeecontext.domain;

import top.dddclub.payroll.employeecontext.port.LeaveRepository;
import top.dddclub.payroll.employeecontext.port.PunchingRecordRepository;

import java.time.LocalDate;
import java.util.Optional;

public class AttendanceStatusService {
    private LeaveRepository leaveRepo;
    private PunchingRecordRepository punchingRepo;

    public AttendanceStatusService(LeaveRepository leaveRepo, PunchingRecordRepository punchingRepo) {
        this.leaveRepo = leaveRepo;
        this.punchingRepo = punchingRepo;
    }

    public AttendanceStatus acquireStatus(String employeeId, LocalDate date, WorkHour workHour) {
        if (leaveRepo.leaveFor(employeeId, date)) {
            return AttendanceStatus.Leave;
        }

        Optional<PunchingRecord> optionalPunchingRecord = punchingRepo.punchingRecordsOf(employeeId, date);
        PunchingRecord punchingRecord = optionalPunchingRecord.get();
        return punchingRecord.acquireStatus(workHour);
    }
}