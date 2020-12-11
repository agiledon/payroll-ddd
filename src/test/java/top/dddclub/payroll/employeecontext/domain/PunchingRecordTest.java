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
        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(17, 0, 0);
        WorkHour workHour = new WorkHour(startTime, endTime);

        String employeeId = "emp0001";
        LocalDateTime punchingIn = LocalDateTime.of(2020, 12, 10, 9, 0, 0);
        LocalDateTime punchingOut = LocalDateTime.of(2020, 12, 10, 17, 0, 0);
        LocalDate today = LocalDate.of(2020, 12, 10);
        PunchingRecord punching = new PunchingRecord(employeeId, punchingIn, punchingOut, today);

        // when
        AttendanceStatus status = punching.acquireStatus(workHour);

        // then
        assertThat(status.isNormal()).isTrue();
    }
}
