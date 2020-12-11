package top.dddclub.payroll.fixture;

import top.dddclub.payroll.employeecontext.domain.WorkHour;

import java.time.LocalTime;

public class AttendanceFixture {
    public static WorkHour createWorkHour() {
        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(17, 0, 0);
        return new WorkHour(startTime, endTime);
    }
}