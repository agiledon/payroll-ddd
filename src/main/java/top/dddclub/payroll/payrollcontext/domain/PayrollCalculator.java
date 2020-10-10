package top.dddclub.payroll.payrollcontext.domain;

import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.util.List;

public interface PayrollCalculator {
    List<Payroll> execute(Period settlementPeriod);
}