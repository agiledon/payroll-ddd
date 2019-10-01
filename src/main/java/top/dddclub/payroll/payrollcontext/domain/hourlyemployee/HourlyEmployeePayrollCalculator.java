package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.Payroll;

import java.util.List;
import java.util.stream.Collectors;

public class HourlyEmployeePayrollCalculator {
    private HourlyEmployeeRepository employeeRepository;

    public void setRepository(HourlyEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Payroll> execute(Period settlementPeriod) {
        List<HourlyEmployee> hourlyEmployees = employeeRepository.allEmployeesOf(settlementPeriod);
        return hourlyEmployees.stream()
                .map(e -> e.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
