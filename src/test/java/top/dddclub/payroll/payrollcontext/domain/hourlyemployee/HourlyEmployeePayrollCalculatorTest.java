package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.junit.Before;
import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Salary;
import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.fixture.EmployeeFixture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static top.dddclub.payroll.fixture.EmployeeFixture.hourlyEmployeeOf;

public class HourlyEmployeePayrollCalculatorTest {

    private Period settlementPeriod;
    private HourlyEmployeeRepository mockRepo;
    private List<HourlyEmployee> hourlyEmployees;
    private HourlyEmployeePayrollCalculator calculator;

    @Before
    public void setUp() {
        settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        mockRepo = mock(HourlyEmployeeRepository.class);
        hourlyEmployees = new ArrayList<>();
        calculator = new HourlyEmployeePayrollCalculator();
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        //given
        when(mockRepo.allEmployeesOf()).thenReturn(new ArrayList<>());

        HourlyEmployeePayrollCalculator calculator = new HourlyEmployeePayrollCalculator();
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();
        assertThat(payrolls).isNotNull().isEmpty();
    }

    @Test
    public void should_calculate_payroll_when_only_one_matched_employee_found() {
        //given
        String employeeId = "emp200901011111";
        HourlyEmployee hourlyEmployee = hourlyEmployeeOf(employeeId, 8, 8, 8, 8, 8);
        hourlyEmployees.add(hourlyEmployee);

        when(mockRepo.allEmployeesOf()).thenReturn(hourlyEmployees);
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(1);
        assertPayroll(employeeId, payrolls, 0, settlementPeriod, 4000.00);
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        //given
        String employeeId1 = "emp200901011111";
        HourlyEmployee hourlyEmployee1 = hourlyEmployeeOf(employeeId1, 8, 8, 8, 8, 8);
        hourlyEmployees.add(hourlyEmployee1);

        String employeeId2 = "emp200901011112";
        HourlyEmployee hourlyEmployee2 = hourlyEmployeeOf(employeeId2, 9, 7, 10, 10, 8);
        hourlyEmployees.add(hourlyEmployee2);

        String employeeId3 = "emp200901011113";
        HourlyEmployee hourlyEmployee3 = EmployeeFixture.hourlyEmployeeOf(employeeId3, null);
        hourlyEmployees.add(hourlyEmployee3);

        when(mockRepo.allEmployeesOf()).thenReturn(hourlyEmployees);
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(3);
        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, 4000.00);
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, 4650.00);
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, 0.00);
    }

    private void assertPayroll(String employeeId, List<Payroll> payrolls, int index, Period settlementPeriod, double payrollAmount) {
        Payroll payroll = payrolls.get(index);
        assertThat(payroll.employeId().value()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(settlementPeriod.beginDate());
        assertThat(payroll.endDate()).isEqualTo(settlementPeriod.endDate());
        assertThat(payroll.amount()).isEqualTo(Salary.of(payrollAmount));
    }
}
