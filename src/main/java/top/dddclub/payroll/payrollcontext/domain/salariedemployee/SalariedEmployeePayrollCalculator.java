package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.util.List;
import java.util.stream.Collectors;

public class SalariedEmployeePayrollCalculator {
    private SalariedEmployeeRepository employeeRepository;

    public void setRepository(SalariedEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Payroll> execute(Period settlementPeriod) {
        List<SalariedEmployee> salariedEmployees = employeeRepository.allEmployeesOf();
        return salariedEmployees.stream()
                .map(e -> e.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
