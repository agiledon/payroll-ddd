package top.dddclub.payroll.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static top.dddclub.payroll.fixture.EmployeeFixture.createHourlyEmployee;

public class HourlyEmployeePayrollCalculatorTest {

    private Period settlementPeriod;
    private HourlyEmployeeRepository mockRepo;

    @Before
    public void setUp() {
        settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        mockRepo = mock(HourlyEmployeeRepository.class);
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        //given
        when(mockRepo.allEmployeesOf(settlementPeriod)).thenReturn(new ArrayList<>());

        HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        //given
        String employeeId = "emp200901011111";
        HourlyEmployee hourlyEmployee = createHourlyEmployee(employeeId, 8, 8, 8, 8, 8);
        ArrayList<HourlyEmployee> hourlyEmployees = new ArrayList<>();
        hourlyEmployees.add(hourlyEmployee);

        when(mockRepo.allEmployeesOf(settlementPeriod)).thenReturn(hourlyEmployees);

        HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf(settlementPeriod);
        assertThat(payrolls).isNotNull().hasSize(1);

        Payroll payroll = payrolls.get(0);
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(settlementPeriod.beginDate());
        assertThat(payroll.endDate()).isEqualTo(settlementPeriod.endDate());
        assertThat(payroll.amount()).isEqualTo(Money.of(4000.00));
    }
}
