package top.dddclub.payroll.employeecontext.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PunchingRecordTest {
    private WorkHour workHour;
    public static final int ARRIVED_HOUR = 9;
    public static final int LEAVE_HOUR = 17;

    @Before
    public void setUp() {
        workHour = createWorkHour();
    }

    @Test
    public void should_acquire_normal_status() {
        // given
        int arrivedMinuets = 15;
        int leaveMinutes = 0;
        PunchingRecord punching = createPunchingRecord(ARRIVED_HOUR, arrivedMinuets, LEAVE_HOUR, leaveMinutes);

        // when
        AttendanceStatus status = punching.acquireStatus(workHour);

        // then
        assertThat(status.isNormal()).isTrue();
    }

    @Test
    public void should_acquire_late_status() {
        // given
        int arrivedMinuets = 16;
        int leaveMinutes = 0;
        PunchingRecord punching = createPunchingRecord(ARRIVED_HOUR, arrivedMinuets, LEAVE_HOUR, leaveMinutes);

        // when
        AttendanceStatus status = punching.acquireStatus(workHour);

        // then
        assertThat(status.isLate()).isTrue();
    }

    private PunchingRecord createPunchingRecord(int arrivedHour, int arrivedMinuets, int leaveHour, int leaveMinutes) {
        String employeeId = "emp0001";
        LocalDate today = LocalDate.of(2020, 12, 10);
        return new PunchingRecord(employeeId, LocalDateTime.of(2020, 12, 10, arrivedHour, arrivedMinuets, 0), LocalDateTime.of(2020, 12, 10, leaveHour, leaveMinutes, 0), today);
    }

    private WorkHour createWorkHour() {
        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(17, 0, 0);
        return new WorkHour(startTime, endTime);
    }
}
