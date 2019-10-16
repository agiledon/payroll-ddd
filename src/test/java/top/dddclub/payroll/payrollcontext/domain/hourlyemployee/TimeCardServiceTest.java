package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.fixture.EmployeeFixture;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeCardServiceTest {
    private String employeeId = "emp200901011111";

    @Test
    public void should_submit_time_card() {
        //given
        TimeCardService timeCardService = new TimeCardService();

        HourlyEmployeeRepository mockRepo = mock(HourlyEmployeeRepository.class);
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, null);
        when(mockRepo.employeeOf(EmployeeId.of(employeeId))).thenReturn(Optional.of(hourlyEmployee));

        timeCardService.setHourlyEmployeeRepository(mockRepo);

        TimeCard newTimeCard = new TimeCard(LocalDate.of(2019, 10, 8), 8);

        //when
        timeCardService.submitTimeCard(EmployeeId.of(employeeId), newTimeCard);

        //then
        assertThat(hourlyEmployee.timeCards()).hasSize(1);
    }
}