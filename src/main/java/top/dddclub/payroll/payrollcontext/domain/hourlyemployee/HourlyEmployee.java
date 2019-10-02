package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.Payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HourlyEmployee {
    private static final double OVERTIME_FACTOR = 1.5;
    private String employeeId;
    private Money salaryOfHour;
    private List<TimeCard> timeCards;

    public HourlyEmployee(String employeeId, Money salaryOfHour) {
        this(employeeId, salaryOfHour, new ArrayList<>());
    }

    public HourlyEmployee(String employeeId, Money salaryOfHour, List<TimeCard> timeCards) {
        this.employeeId = employeeId;
        this.salaryOfHour = salaryOfHour;
        this.timeCards = timeCards;
    }

    public Payroll payroll(Period period) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(this.employeeId, period.beginDate(), period.endDate(), Money.zero());
        }

        Money regularSalary = calculateRegularSalary();
        Money overtimeSalary = calculateOvertimeSalary();
        Money totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(this.employeeId, period.beginDate(), period.endDate(), totalSalary);
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
