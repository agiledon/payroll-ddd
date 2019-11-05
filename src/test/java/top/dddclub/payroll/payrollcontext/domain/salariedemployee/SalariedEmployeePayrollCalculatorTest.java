package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import org.junit.Before;
import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.Salary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static top.dddclub.payroll.fixture.EmployeeFixture.salariedEmployeeOf;
import static top.dddclub.payroll.fixture.EmployeeFixture.salariedEmployeeWithOneSickLeaveOf;

public class SalariedEmployeePayrollCalculatorTest {
    private Period settlementPeriod;
    private SalariedEmployeeRepository mockRepo;
    private List<SalariedEmployee> salariedEmployees;
    private SalariedEmployeePayrollCalculator calculator;

    @Before
    public void setup() {
        settlementPeriod = new Period(LocalDate.of(2019, 9, 2), LocalDate.of(2019, 9, 6));
        mockRepo = mock(SalariedEmployeeRepository.class);
        salariedEmployees = new ArrayList<>();
        calculator = new SalariedEmployeePayrollCalculator();
    }

    @Test
    public void should_calculate_payroll_when_no_matched_employee_found() {
        //given
        when(mockRepo.allEmployeesOf()).thenReturn(new ArrayList<>());
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
        SalariedEmployee salariedEmployee = salariedEmployeeOf(employeeId, 10000.00);
        salariedEmployees.add(salariedEmployee);

        when(mockRepo.allEmployeesOf()).thenReturn(salariedEmployees);
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(1);
        assertPayroll(employeeId, payrolls, 0, settlementPeriod, 10000.00);
    }

    @Test
    public void should_calculate_payroll_when_more_than_one_matched_employee_found() {
        //given
        String employeeId1 = "emp200901011111";
        SalariedEmployee salariedEmployee1 = salariedEmployeeWithOneSickLeaveOf(employeeId1, 10000.00);
        salariedEmployees.add(salariedEmployee1);

        String employeeId2 = "emp200901011112";
        SalariedEmployee salariedEmployee2 = salariedEmployeeOf(employeeId2, 5000.00);
        salariedEmployees.add(salariedEmployee2);

        String employeeId3 = "emp200901011113";
        SalariedEmployee salariedEmployee3 = salariedEmployeeOf(employeeId3, 8000.00);
        salariedEmployees.add(salariedEmployee3);

        when(mockRepo.allEmployeesOf()).thenReturn(salariedEmployees);
        calculator.setRepository(mockRepo);

        //when
        List<Payroll> payrolls = calculator.execute(settlementPeriod);

        //then
        verify(mockRepo, times(1)).allEmployeesOf();

        assertThat(payrolls).isNotNull().hasSize(3);
        assertPayroll(employeeId1, payrolls, 0, settlementPeriod, 9772.73);
        assertPayroll(employeeId2, payrolls, 1, settlementPeriod, 5000.00);
        assertPayroll(employeeId3, payrolls, 2, settlementPeriod, 8000.00);
    }

    private void assertPayroll(String employeeId, List<Payroll> payrolls, int index, Period settlementPeriod, double payrollAmount) {
        Payroll payroll = payrolls.get(index);
        assertThat(payroll.employeId().value()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(settlementPeriod.beginDate());
        assertThat(payroll.endDate()).isEqualTo(settlementPeriod.endDate());
        assertThat(payroll.amount()).isEqualTo(Salary.of(payrollAmount));
    }
}
