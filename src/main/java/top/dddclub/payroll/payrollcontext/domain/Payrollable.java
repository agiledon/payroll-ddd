package top.dddclub.payroll.payrollcontext.domain;

public interface Payrollable {
    Payroll payroll(Period settlementPeriod);
}