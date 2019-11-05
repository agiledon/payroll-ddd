package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.PayrollCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class SalariedEmployeePayrollCalculator implements PayrollCalculator {
    private SalariedEmployeeRepository employeeRepository;

    public SalariedEmployeePayrollCalculator(SalariedEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Payroll> execute(Period settlementPeriod) {
        List<SalariedEmployee> salariedEmployees = employeeRepository.allEmployeesOf();
        return salariedEmployees.stream()
                .map(e -> e.payroll(settlementPeriod))
                .collect(Collectors.toList());
    }
}
