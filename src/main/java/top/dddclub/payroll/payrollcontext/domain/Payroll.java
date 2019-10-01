package top.dddclub.payroll.payrollcontext.domain;

import java.time.LocalDate;

public class Payroll {
    private String employeeId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Money amount;

    public Payroll(String employeeId, LocalDate beginDate, LocalDate endDate, Money amount) {
        this.employeeId = employeeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public String employeId() {
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
