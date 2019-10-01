package top.dddclub.payroll.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HourlyEmployeePayrollCalculatorTest {
    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        //given
        Period settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        HourlyEmployeeRepository mockRepo = mock(HourlyEmployeeRepository.class);
        when(mockRepo.allEmployeesOf(settlementPeriod)).thenReturn(new ArrayList<>());

        HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().isEmpty();
    }
}
