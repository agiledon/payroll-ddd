package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;
import top.dddclub.payroll.fixture.EmployeeFixture;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitTimeCardTest {
    private final String employeeId = "emp200901011111";

    @Test
    public void should_submit_valid_time_cards() {
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, null);
        List<TimeCard> timeCards = EmployeeFixture.createTimeCards(8, 8, 8, 8, 8);

        assertThat(hourlyEmployee.timeCards()).isNotNull().isEmpty();

        hourlyEmployee.submit(timeCards);

        assertThat(hourlyEmployee.timeCards()).hasSize(5);
    }

    @Test
    public void should_not_submit_time_cards_with_same_work_day() {
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, 8, 8, 8, 8, 8);
        List<TimeCard> repeatedTimeCards = EmployeeFixture.createTimeCards(8, 8, 8, 8, 8);

        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        hourlyEmployee.submit(repeatedTimeCards);

        assertThat(hourlyEmployee.timeCards()).hasSize(5);
    }

    @Test
    public void should_submit_a_valid_time_cards() {
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, null);
        TimeCard timeCard = new TimeCard(LocalDate.of(2019, 9,2), 8);

        assertThat(hourlyEmployee.timeCards()).isNotNull().isEmpty();

        hourlyEmployee.submit(timeCard);

        assertThat(hourlyEmployee.timeCards()).hasSize(1);
    }

    @Test
    public void should_not_submit_time_card_with_same_word_day() {
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, 8, 8, 8, 8, 8);
        TimeCard repeatedCard = new TimeCard(LocalDate.of(2019, 9,2), 8);
        TimeCard newCard = new TimeCard(LocalDate.of(2019, 9,10), 8);

        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        hourlyEmployee.submit(repeatedCard);
        hourlyEmployee.submit(newCard);

        assertThat(hourlyEmployee.timeCards()).hasSize(6);
    }
}