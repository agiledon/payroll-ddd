package top.dddclub.payroll.domain;

import java.time.LocalDate;

class Period {
    private LocalDate beginDate;
    private LocalDate endDate;

    Period(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public LocalDate beginDate() {
        return beginDate;
    }

    public LocalDate endDate() {
        return endDate;
    }
}
