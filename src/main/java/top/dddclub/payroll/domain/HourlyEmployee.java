package top.dddclub.payroll.domain;

import java.time.LocalDate;
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

        return new Payroll(
                settlementPeriod().beginDate,
                settlementPeriod().endDate,
                salaryOfHour.multiply(totalHours));
    }

    private Period settlementPeriod() {
        Collections.sort(timeCards);

        LocalDate beginDate = timeCards.get(0).workDay();
        LocalDate endDate = timeCards.get(timeCards.size() - 1).workDay();
        return new Period(beginDate, endDate);
    }

    private class Period {
        private LocalDate beginDate;
        private LocalDate endDate;

        Period(LocalDate beginDate, LocalDate endDate) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        }
    }
}
