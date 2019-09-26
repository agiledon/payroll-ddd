package top.dddclub.payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HourlyEmployeeTest {
    @Test
    public void should_calculate_payroll_by_work_hours_in_a_week() {
        //given
        TimeCard timeCard1 = new TimeCard(LocalDate.of(2019, 9, 2), 8);
        TimeCard timeCard2 = new TimeCard(LocalDate.of(2019, 9, 3), 8);
        TimeCard timeCard3 = new TimeCard(LocalDate.of(2019, 9, 4), 8);
        TimeCard timeCard4 = new TimeCard(LocalDate.of(2019, 9, 5), 8);
        TimeCard timeCard5 = new TimeCard(LocalDate.of(2019, 9, 6), 8);

        List<TimeCard> timeCards = new ArrayList<>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
        timeCards.add(timeCard4);
        timeCards.add(timeCard5);

        HourlyEmployee hourlyEmployee = new HourlyEmployee(timeCards, Money.of(10000, Currency.RMB));

        //when
        Payroll payroll = hourlyEmployee.payroll();

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.beginDate()).isEqualTo(LocalDate.of(2019, 9, 2));
        assertThat(payroll.endDate()).isEqualTo(LocalDate.of(2019, 9, 6));
        assertThat(payroll.amount()).isEqualTo(Money.of(400000, Currency.RMB));
    }
}
