package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.fixture.EmployeeFixture;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TimeCardServiceTest {
    private String employeeId = "emp200901011111";

    @Test
    public void should_submit_time_card() {
        //given
        EmployeeId employeeIdObj = EmployeeId.of(employeeId);

        TimeCardService timeCardService = new TimeCardService();

        HourlyEmployeeRepository mockRepo = mock(HourlyEmployeeRepository.class);
        HourlyEmployee hourlyEmployee = EmployeeFixture.hourlyEmployeeOf(employeeId, null);
        when(mockRepo.employeeOf(employeeIdObj)).thenReturn(Optional.of(hourlyEmployee));
        timeCardService.setEmployeeRepository(mockRepo);

        TimeCard newTimeCard = new TimeCard(LocalDate.of(2019, 10, 8), 8);

        //when
        timeCardService.submitTimeCard(employeeIdObj, newTimeCard);

        //then
        verify(mockRepo).employeeOf(employeeIdObj);
        verify(mockRepo).save(hourlyEmployee);
        assertThat(hourlyEmployee.timeCards()).hasSize(1);
    }
}