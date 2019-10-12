package top.dddclub.payroll.employeecontext.domain;

import top.dddclub.payroll.core.domain.AbstractEntity;
import top.dddclub.payroll.core.domain.AggregateRoot;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="employees")
public class Employee extends AbstractEntity<EmployeeId> implements AggregateRoot<Employee> {
    @EmbeddedId
    private EmployeeId employeeId;

    private String name;

    @Embedded
    private Email email;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @Embedded
    private Contact contact;

    private LocalDate onBoardingDate;

    @Override
    public EmployeeId id() {
        return employeeId;
    }

    public String name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public boolean isHourly() {
        return employeeType.equals(EmployeeType.Hourly);
    }

    public boolean isSalaried() {
        return employeeType.equals(EmployeeType.Salaried);
    }

    public boolean isCommission() {
        return employeeType.equals(EmployeeType.Commission);
    }

    public boolean isMale() {
        return gender.equals(Gender.Male);
    }

    public boolean isFemale() {
        return gender.equals(Gender.Female);
    }

    public Address address() {
        return address;
    }

    public Contact contact() {
        return contact;
    }

    public LocalDate boardingDate() {
        return onBoardingDate;
    }

    @Override
    public Employee root() {
        return this;
    }
}
