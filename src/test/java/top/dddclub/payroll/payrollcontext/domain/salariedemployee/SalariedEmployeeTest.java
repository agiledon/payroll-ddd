package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import org.junit.Test;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariedEmployeeTest {
    private final Period settlementPeriod = new Period(YearMonth.of(2019, 9));
    private final String employeeId = "emp200901011111";
    private Money salaryOfMonth = Money.of(10000.00);

    @Test
    public void should_return_monthly_salary_if_employee_without_absence() {
        //given
        SalariedEmployee salariedEmployee = new SalariedEmployee(employeeId, salaryOfMonth);

        //when
        Payroll payroll = salariedEmployee.payroll(settlementPeriod);

        //then
        assertThat(payroll).isNotNull();
        assertThat(payroll.employeId()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(LocalDate.of(2019, 9, 1));
        assertThat(payroll.beginDate()).isEqualTo(LocalDate.of(2019, 9, 30));
        assertThat(payroll.amount()).isEqualTo(salaryOfMonth);
    }
}
