package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

public class SalariedEmployee {
    public SalariedEmployee(String employeeId, Money salaryOfMonth) {
    }

    public Payroll payroll(Period settlementPeriod) {
        return null;
    }
}
