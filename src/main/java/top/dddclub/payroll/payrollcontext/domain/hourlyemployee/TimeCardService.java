package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import top.dddclub.payroll.employeecontext.domain.EmployeeId;

public class TimeCardService {
    private HourlyEmployeeRepository hourlyEmployeeRepository;

    public void setHourlyEmployeeRepository(HourlyEmployeeRepository hourlyEmployeeRepository) {

        this.hourlyEmployeeRepository = hourlyEmployeeRepository;
    }

    public void submitTimeCard(EmployeeId employeeId, TimeCard submitted) {


    }
}
