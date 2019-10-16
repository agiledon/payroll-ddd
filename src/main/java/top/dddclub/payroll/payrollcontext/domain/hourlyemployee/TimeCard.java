package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.payrollcontext.domain.Period;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "timecards")
public class TimeCard {
    private static final int MAXIMUM_REGULAR_HOURS = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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

    public boolean isIn(Period period) {
        return period.contains(workDay);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeCard timeCard = (TimeCard) o;
        return Objects.equals(workDay, timeCard.workDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDay);
    }
}
