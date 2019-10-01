package top.dddclub.payroll.fixture;

import top.dddclub.payroll.payrollcontext.domain.Currency;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFixture {
    public static HourlyEmployee hourlyEmployeeOf(String employeeId, List<TimeCard> timeCards) {
        Money salaryOfHour = Money.of(100.00, Currency.RMB);
        return new HourlyEmployee(employeeId, timeCards, salaryOfHour);
    }

    public static HourlyEmployee hourlyEmployeeOf(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        Money salaryOfHour = Money.of(100.00, Currency.RMB);
        List<TimeCard> timeCards = createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5);
        return new HourlyEmployee(employeeId, timeCards, salaryOfHour);
    }

    private static List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 9, 2), workHours1);
        TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 9, 3), workHours2);
        TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 9, 4), workHours3);
        TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 9, 5), workHours4);
        TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 9, 6), workHours5);

        List<TimeCard> timeCards = new ArrayList<>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
        timeCards.add(timeCard4);
        timeCards.add(timeCard5);
        return timeCards;
    }
}
