package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.util.List;
import java.util.Optional;

public interface HourlyEmployeeRepository {
    Optional<HourlyEmployee> employeeOf(EmployeeId employeeId);
    List<HourlyEmployee> allEmployeesOf();
    void save(HourlyEmployee employee);
}
