package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import java.util.List;

public interface SalariedEmployeeRepository {
    List<SalariedEmployee> allEmployeesOf();
}