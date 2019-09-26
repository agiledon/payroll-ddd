package top.dddclub.payroll.domain;

import java.time.LocalDate;

public class TimeCard implements Comparable<TimeCard> {
    private LocalDate workDay;
    private int workHours;

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

    @Override
    public int compareTo(TimeCard o) {
        if (workDay.isBefore(o.workDay)) {
            return -1;
        }
        if (workDay.isAfter(o.workDay)) {
            return 1;
        }
        return 0;
    }
}
