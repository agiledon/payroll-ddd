package top.dddclub.payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HourlyEmployeeTest {

    private final Period settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
    private final Money salaryOfHour = Money.of(100.00, Currency.RMB);
    private final String employeeId = "emp200901011111";

    @Test
    public void should_calculate_payroll_by_work_hours_in_a_week() {
        //given
        List<TimeCard> timeCards = createTimeCards(8, 8, 8, 8, 8);
        HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, timeCards, salaryOfHour);

        //when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(LocalDate.of(2019, 9, 2));
        assertThat(payroll.endDate()).isEqualTo(LocalDate.of(2019, 9, 6));
        assertThat(payroll.amount()).isEqualTo(Money.of(4000.00, Currency.RMB));
    }

    @Test
    public void should_calculate_payroll_by_work_hours_with_overtime_in_a_week() {
        //given
        List<TimeCard> timeCards = createTimeCards(9, 7, 10, 10, 8);
        HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, timeCards, salaryOfHour);

        //when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(LocalDate.of(2019, 9, 2));
        assertThat(payroll.endDate()).isEqualTo(LocalDate.of(2019, 9, 6));
        assertThat(payroll.amount()).isEqualTo(Money.of(4650.00, Currency.RMB));
    }

    @Test
    public void should_be_0_given_no_any_timecard() {
        //given
        HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, new ArrayList<>(), salaryOfHour);

        //when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.amount()).isEqualTo(Money.of(0.00, Currency.RMB));
    }

    @Test
    public void should_be_0_given_null_timecard() {
        //given
        HourlyEmployee hourlyEmployee = new HourlyEmployee(employeeId, null, salaryOfHour);

        //when
        Payroll payroll = hourlyEmployee.payroll(settlementPeriod);

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.amount()).isEqualTo(Money.zero());
    }

    private List<TimeCard> createTimeCards(int workHours1, int workHours2, int workHours3, int workHours4, int workHours5) {
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
