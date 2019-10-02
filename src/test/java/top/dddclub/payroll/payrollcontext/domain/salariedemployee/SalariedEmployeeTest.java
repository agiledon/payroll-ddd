package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static top.dddclub.payroll.fixture.EmployeeFixture.*;

public class SalariedEmployeeTest {
    private final Period settlementPeriod = new Period(2019, 9);
    private final String employeeId = "emp200901011111";
    private Money salaryOfMonth = Money.of(10000.00);

    @Test
    public void should_return_monthly_salary_if_employee_without_absence() {
        //given
        SalariedEmployee salariedEmployee = salariedEmployeeOf(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                salaryOfMonth);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_sick_leave() {
        //given
        SalariedEmployee salariedEmployee = salariedEmployeeWithOneSickLeaveOf(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money expectedAmount = Money.of(9772.73);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                expectedAmount);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_casual_leave() {
        //given
        SalariedEmployee salariedEmployee = salariedEmployeeWithOneCasualLeaveOf(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money expectedAmount = Money.of(9772.73);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                expectedAmount);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_paid_leave() {
        //given
        SalariedEmployee salariedEmployee = salariedEmployeeWithOnePaidLeaveOf(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money expectedAmount = Money.of(10000.00);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                expectedAmount);
    }

    @Test
    public void should_deduct_salary_if_employee_ask_one_day_leave_which_is_disapproved() {
        //given
        SalariedEmployee salariedEmployee = salariedEmployeeWithOneDisapprovedLeaveOf(employeeId);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money expectedAmount = Money.of(9545.46);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                expectedAmount);
    }

    private void assertPayroll(Payroll payroll, String employeeId, LocalDate beginDate, LocalDate endDate, Money payrollAmount) {
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(beginDate);
        assertThat(payroll.endDate()).isEqualTo(endDate);
        assertThat(payroll.amount()).isEqualTo(payrollAmount);
    }
}
