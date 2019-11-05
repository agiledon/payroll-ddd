package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import org.junit.Before;
import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SalariedEmployeePayrollCalculatorTest {
    private Period settlementPeriod;
    private SalariedEmployeeRepository mockRepo;

    @Before
    public void setup() {
        settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        mockRepo = mock(SalariedEmployeeRepository.class);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        //given
        when(mockRepo.allEmployeesOf()).thenReturn(new ArrayList<>());

        SalariedEmployeePayrollCalculator calculator = new SalariedEmployeePayrollCalculator();
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().isEmpty();
    }
}
