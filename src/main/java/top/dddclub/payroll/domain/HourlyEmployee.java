package top.dddclub.payroll.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class HourlyEmployee {
    private static final double OVERTIME_FACTOR = 1.5;
    private List<TimeCard> timeCards;
    private Money salaryOfHour;

    public HourlyEmployee(List<TimeCard> timeCards, Money salaryOfHour) {
        this.timeCards = timeCards;
        this.salaryOfHour = salaryOfHour;
    }

    public Payroll payroll(Period period) {
        Money regularSalary = calculateRegularSalary();
        Money overtimeSalary = calculateOvertimeSalary();
        Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(
                period.beginDate(),
                period.endDate(),
                totalSalary);
    }

    private Money calculateRegularSalary() {
        int regularHours = timeCards.stream()
                .map(TimeCard::getRegularWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(regularHours);
    }

    private Money calculateOvertimeSalary() {
        int overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);

        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
    }
}
