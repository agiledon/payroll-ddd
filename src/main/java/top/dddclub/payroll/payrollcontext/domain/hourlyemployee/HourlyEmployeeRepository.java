package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.payrollcontext.domain.Period;

import java.util.List;

public interface HourlyEmployeeRepository {
    List<HourlyEmployee> allEmployeesOf(Period settlementPeriod);
}
