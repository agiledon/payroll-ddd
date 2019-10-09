package top.dddclub.payroll.payrollcontext.domain;

import top.dddclub.payroll.employeecontext.domain.EmployeeId;

import java.time.LocalDate;

public class Payroll {
    private EmployeeId employeeId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Money amount;

    public Payroll(EmployeeId employeeId, LocalDate beginDate, LocalDate endDate, Money amount) {
        this.employeeId = employeeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public EmployeeId employeId() {
        return this.employeeId;
    }

    public LocalDate beginDate() {
        return this.beginDate;
    }

    public LocalDate endDate() {
        return this.endDate;
    }

    public Money amount() {
        return this.amount;
    }
}
