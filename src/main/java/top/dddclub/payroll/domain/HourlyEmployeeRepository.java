package top.dddclub.payroll.domain;

import java.util.List;

public interface HourlyEmployeeRepository {
    List<HourlyEmployee> allEmployeesOf(Period settlementPeriod);
}
