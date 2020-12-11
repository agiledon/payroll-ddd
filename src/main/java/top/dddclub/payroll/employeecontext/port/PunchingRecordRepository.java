package top.dddclub.payroll.employeecontext.port;

import top.dddclub.payroll.employeecontext.domain.PunchingRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface PunchingRecordRepository {
    Optional<PunchingRecord> punchingRecordsOf(String employeeId, LocalDate date);
}
