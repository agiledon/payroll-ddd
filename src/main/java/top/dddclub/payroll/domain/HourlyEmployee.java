package top.dddclub.payroll.domain;

import java.util.Collections;
import java.util.List;

public class HourlyEmployee {
    private List<TimeCard> timeCards;
    private Money salaryOfHour;

    public HourlyEmployee(List<TimeCard> timeCards, Money salaryOfHour) {
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll() {
        int totalHours = timeCards.stream()
                .map(tc -> tc.workHours())
                .reduce(0, (hours, total) -> hours + total);

        Collections.sort(timeCards);

        return new Payroll(timeCards.get(0).workDay(), timeCards.get(timeCards.size() - 1).workDay(), salaryOfHour.multiply(totalHours));
    }
}
