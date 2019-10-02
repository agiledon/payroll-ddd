package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

public class SalariedEmployee {
    private String employeeId;
    private Money salaryOfMonth;

    public SalariedEmployee(String employeeId, Money salaryOfMonth) {
        this.employeeId = employeeId;
        this.salaryOfMonth = salaryOfMonth;
    }

    public Payroll payroll(Period settlementPeriod) {
        return new Payroll(employeeId, settlementPeriod.beginDate(), settlementPeriod.endDate(), salaryOfMonth);
    }
}
