package top.dddclub.payroll.payrollcontext.domain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PayrollCalculatorTest {
    protected void assertPayroll(String employeeId, List<Payroll> payrolls, int index, Period settlementPeriod, double payrollAmount) {
        Payroll payroll = payrolls.get(index);
        assertThat(payroll.employeId().value()).isEqualTo(employeeId);
        assertThat(payroll.beginDate()).isEqualTo(settlementPeriod.beginDate());
        assertThat(payroll.endDate()).isEqualTo(settlementPeriod.endDate());
        assertThat(payroll.amount()).isEqualTo(Salary.of(payrollAmount));
    }
}
