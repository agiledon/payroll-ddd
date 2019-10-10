package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "timecards")
public class TimeCard {
    private static final int MAXIMUM_REGULAR_HOURS = 8;

    @Id
    @GeneratedValue
    private String id;
    private LocalDate workDay;
    private int workHours;

    public TimeCard() {
    }

    public TimeCard(LocalDate workDay, int workHours) {
        this.workDay = workDay;
        this.workHours = workHours;
    }

    public int workHours() {
        return this.workHours;
    }

    public LocalDate workDay() {
        return this.workDay;
    }

    public boolean isOvertime() {
        return workHours() > MAXIMUM_REGULAR_HOURS;
    }

    public int getOvertimeWorkHours() {
        return workHours() - MAXIMUM_REGULAR_HOURS;
    }

    public int getRegularWorkHours() {
        return isOvertime() ? MAXIMUM_REGULAR_HOURS : workHours();
    }
}
