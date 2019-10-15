package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;
import top.dddclub.payroll.fixture.EmployeeFixture;

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

}