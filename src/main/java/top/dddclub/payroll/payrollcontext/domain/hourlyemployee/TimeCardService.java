package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import java.util.Optional;

public class TimeCardService {
    private HourlyEmployeeRepository employeeRepository;

    public void setEmployeeRepository(HourlyEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void submitTimeCard(EmployeeId employeeId, TimeCard submitted) {
        Optional<HourlyEmployee> optEmployee = employeeRepository.employeeOf(employeeId);
        optEmployee.ifPresent(e -> {
            e.submit(submitted);
            employeeRepository.save(e);
        });
    }
}
