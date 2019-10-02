package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariedEmployeeTest {
    private final Period settlementPeriod = new Period(2019, 9);
    private final String employeeId = "emp200901011111";
    private Money salaryOfMonth = Money.of(10000.00);

    @Test
    public void should_return_monthly_salary_if_employee_without_absence() {
        //given
        SalariedEmployee salariedEmployee = new SalariedEmployee(employeeId, salaryOfMonth);

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
        Absence sickLeave = new Absence(employeeId, LocalDate.of(2019, 9, 2), LeaveReason.SickLeave);
        List<Absence> absences = new ArrayList<>();
        absences.add(sickLeave);

        SalariedEmployee salariedEmployee = new SalariedEmployee(employeeId, salaryOfMonth, absences);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        Money payrollAmount = Money.of(9772.72);
        assertPayroll(payroll,
                employeeId,
                LocalDate.of(2019, 9, 1),
                LocalDate.of(2019, 9, 30),
                payrollAmount);
    }

    private void assertPayroll(Payroll payroll, String employeeId, LocalDate beginDate, LocalDate endDate, Money payrollAmount) {
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(beginDate);
        assertThat(payroll.endDate()).isEqualTo(endDate);
        assertThat(payroll.amount()).isEqualTo(payrollAmount);
    }
}
