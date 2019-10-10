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
        this.timeCards = timeCards;
    }

    public Salary salaryOfHour() {
        return this.salaryOfHour;
    }

    public List<TimeCard> timeCards() {
        return this.timeCards;
    }

    public Payroll payroll(Period period) {
        if (Objects.isNull(timeCards) || timeCards.isEmpty()) {
            return new Payroll(this.employeeId, period.beginDate(), period.endDate(), Salary.zero());
        }

        Salary regularSalary = calculateRegularSalary();
        Salary overtimeSalary = calculateOvertimeSalary();
        Salary totalSalary = regularSalary.add(overtimeSalary);

        return new Payroll(this.employeeId, period.beginDate(), period.endDate(), totalSalary);
    }

    private Salary calculateRegularSalary() {
        int regularHours = timeCards.stream()
                .map(TimeCard::getRegularWorkHours)
                .reduce(0, Integer::sum);
        return salaryOfHour.multiply(regularHours);
    }

    private Salary calculateOvertimeSalary() {
        int overtimeHours = timeCards.stream()
                .filter(TimeCard::isOvertime)
                .map(TimeCard::getOvertimeWorkHours)
                .reduce(0, Integer::sum);

        return salaryOfHour.multiply(OVERTIME_FACTOR).multiply(overtimeHours);
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
