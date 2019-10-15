package top.dddclub.payroll.payrollcontext.domain.hourlyemployee;

import org.hibernate.annotations.DiscriminatorOptions;
import top.dddclub.payroll.core.domain.AbstractEntity;
import top.dddclub.payroll.core.domain.AggregateRoot;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.payrollcontext.domain.Payroll;
import top.dddclub.payroll.payrollcontext.domain.Period;
import top.dddclub.payroll.payrollcontext.domain.Salary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "employeeType", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(force=true)
@DiscriminatorValue(value = "0")
public class HourlyEmployee extends AbstractEntity<EmployeeId> implements AggregateRoot<HourlyEmployee> {
    private static final double OVERTIME_FACTOR = 1.5;

    @EmbeddedId
    private EmployeeId employeeId;

    @Embedded
    private Salary salaryOfHour;

    @OneToMany
    @JoinColumn(name = "employeeId", nullable = false)
    private List<TimeCard> timeCards = new ArrayList<>();

    public HourlyEmployee() {
    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour) {
        this(employeeId, salaryOfHour, new ArrayList<>());
    }

    public HourlyEmployee(EmployeeId employeeId, Salary salaryOfHour, List<TimeCard> timeCards) {
        this.employeeId = employeeId;
        this.salaryOfHour = salaryOfHour;

        if (!Objects.isNull(timeCards)) {
            this.timeCards = timeCards;
        }
    }

    public Salary salaryOfHour() {
        return this.salaryOfHour;
    }

    public List<TimeCard> timeCards() {
        return this.timeCards;
    }

    public Payroll payroll(Period settlementPeriod) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(this.employeeId, settlementPeriod.beginDate(), settlementPeriod.endDate(), Salary.zero());
        }

        Salary regularSalary = calculateRegularSalary(settlementPeriod);
        Salary overtimeSalary = calculateOvertimeSalary(settlementPeriod);
        Salary totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(this.employeeId, settlementPeriod.beginDate(), settlementPeriod.endDate(), totalSalary);
    }

    private Salary calculateRegularSalary(Period period) {
        int regularHours = filterByPeriod(period)
                .map(TimeCard::getRegularWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(regularHours);
    }

    private Salary calculateOvertimeSalary(Period period) {
        int overtimeHours = filterByPeriod(period)
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);

        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
    }

    private Stream<TimeCard> filterByPeriod(Period period) {
        return timeCards.stream()
                .filter(t -> t.isIn(period));
    }

    public void submit(List<TimeCard> submittedTimeCards) {
        for (TimeCard card : submittedTimeCards) {
            if (!this.timeCards.contains(card)) {
                this.timeCards.add(card);
            }
        }
    }

    @Override
    public EmployeeId id() {
        return this.employeeId;
    }

    @Override
    public HourlyEmployee root() {
        return this;
    }
}
