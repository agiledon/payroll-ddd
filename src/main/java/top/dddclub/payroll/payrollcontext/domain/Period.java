package top.dddclub.payroll.payrollcontext.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public class Period {
    private LocalDate beginDate;
    private LocalDate endDate;

    public Period(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Period(YearMonth month) {
    }

    public LocalDate beginDate() {
        return beginDate;
    }

    public LocalDate endDate() {
        return endDate;
    }
}
