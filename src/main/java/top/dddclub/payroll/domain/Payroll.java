package top.dddclub.payroll.domain;

import java.time.LocalDate;

public class Payroll {
    private LocalDate beginDate;
    private LocalDate endDate;
    private Money amount;

    public Payroll(LocalDate beginDate, LocalDate endDate, Money amount) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
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
