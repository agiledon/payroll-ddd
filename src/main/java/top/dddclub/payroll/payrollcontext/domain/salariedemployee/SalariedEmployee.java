package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;

import java.util.ArrayList;
import java.util.List;

public class SalariedEmployee {
    private static final int WORK_DAYS_OF_MONTH = 22;
    private String employeeId;
    private Money salaryOfMonth;
    private List<Absence> absences;

    public SalariedEmployee(String employeeId, Money salaryOfMonth) {
        this(employeeId, salaryOfMonth, new ArrayList<>());
    }

    public SalariedEmployee(String employeeId, Money salaryOfMonth, List<Absence> absences) {
        this.employeeId = employeeId;
        this.salaryOfMonth = salaryOfMonth;
        this.absences = absences;
    }

    public Payroll payroll(Period settlementPeriod) {
        Money salaryOfDay = salaryOfMonth.divide(WORK_DAYS_OF_MONTH);

        Money deduction = absences.stream()
                .filter(a -> !a.isPaidLeave())
                .map(a -> salaryOfDay.multiply(a.deductionRatio()))
                .reduce(Money.zero(salaryOfDay.currency()), (m, agg) -> agg.add(m));

        return new Payroll(employeeId, settlementPeriod.beginDate(), settlementPeriod.endDate(), salaryOfMonth.subtract(deduction));
    }
}
