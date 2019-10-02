package top.dddclub.payroll.fixture;

import top.dddclub.payroll.payrollcontext.domain.Currency;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import top.dddclub.payroll.payrollcontext.domain.salariedemployee.Absence;
import top.dddclub.payroll.payrollcontext.domain.salariedemployee.LeaveReason;
import top.dddclub.payroll.payrollcontext.domain.salariedemployee.SalariedEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeFixture {
    public static HourlyEmployee hourlyEmployeeOf(String employeeId, List<TimeCard> timeCards) {
        Money salaryOfHour = Money.of(100.00, Currency.RMB);
        return new HourlyEmployee(employeeId, salaryOfHour, timeCards);
    }

    public static HourlyEmployee hourlyEmployeeOf(String employeeId, int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
        Money salaryOfHour = Money.of(100.00, Currency.RMB);
        List<TimeCard> timeCards = createTimeCards(workHours1, workHours2, workHours3, workHours4, workHours5);
        return new HourlyEmployee(employeeId, salaryOfHour, timeCards);
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

    public static SalariedEmployee salariedEmployeeOf(String employeeId) {
        Money salaryOfMonth = Money.of(10000.00);
        return new SalariedEmployee(employeeId, salaryOfMonth);
    }

    public static SalariedEmployee salariedEmployeeWithOneSickLeaveOf(String employeeId) {
        Absence sickLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.SickLeave);
        return createSalariedEmployeeWithAbsences(employeeId, sickLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneCasualLeaveOf(String employeeId) {
        Absence casualLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.CasualLeave);
        return createSalariedEmployeeWithAbsences(employeeId, casualLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOnePaidLeaveOf(String employeeId) {
        Absence paidLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.MaternityLeave);
        return createSalariedEmployeeWithAbsences(employeeId, paidLeave);
    }

    public static SalariedEmployee salariedEmployeeWithOneDisapprovedLeaveOf(String employeeId) {
        Absence disapprovedLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.DisapprovedLeave);
        return createSalariedEmployeeWithAbsences(employeeId, disapprovedLeave);
    }

    public static SalariedEmployee salariedEmployeeWithManyLeavesOf(String employeeId) {
        Absence sickLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.SickLeave);
        Absence casualLeave = new Absence(employeeId, LocalDate.of(2019, 9, 3), LeaveReason.CasualLeave);
        Absence paidLeave = new Absence(employeeId, LocalDate.of(2019, 9, 4), LeaveReason.MaternityLeave);
        Absence disapprovedLeave = new Absence(employeeId, LocalDate.of(2019, 9, 5), LeaveReason.DisapprovedLeave);

        return createSalariedEmployeeWithAbsences(employeeId, sickLeave, casualLeave, paidLeave, disapprovedLeave);
    }

    private static SalariedEmployee createSalariedEmployeeWithAbsences(String employeeId, Absence... leaves) {
        List<Absence> absences = new ArrayList<>(Arrays.asList(leaves));
        Money salaryOfMonth = Money.of(10000.00);

        return new SalariedEmployee(employeeId, salaryOfMonth, absences);
    }
}
