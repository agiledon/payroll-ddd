package top.dddclub.payroll.employeecontext.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PunchingRecordTest {
    @Test
    public void should_acquire_normal_status() {
        // given
        WorkHour workHour = createWorkHour();
        PunchingRecord punching = createPunchingRecord();

        // when
        AttendanceStatus status = punching.acquireStatus(workHour);

        // then
        assertThat(status.isNormal()).isTrue();
    }

    private PunchingRecord createPunchingRecord() {
        String employeeId = "emp0001";
        LocalDateTime punchingIn = LocalDateTime.of(2020, 12, 10, 9, 0, 0);
        LocalDateTime punchingOut = LocalDateTime.of(2020, 12, 10, 17, 0, 0);
        LocalDate today = LocalDate.of(2020, 12, 10);
        return new PunchingRecord(employeeId, punchingIn, punchingOut, today);
    }

    private WorkHour createWorkHour() {
        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(17, 0, 0);
        return new WorkHour(startTime, endTime);
    }
}
