package top.dddclub.payroll.core.gateway.persistence;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.domain.Address;
import top.dddclub.payroll.employeecontext.domain.Contact;
import top.dddclub.payroll.employeecontext.domain.Employee;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.payrollcontext.domain.Salary;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.TimeCard;
import top.dddclub.payroll.payrollcontext.domain.salariedemployee.Absence;
import top.dddclub.payroll.payrollcontext.domain.salariedemployee.SalariedEmployee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryIT {
    private static final String PERSISTENCE_UNIT_NAME = "PAYROLL_JPA";

    @Test
    public void should_query_employee_table_by_id() {
        //given
        String employeeId = "emp200109101000001";
        Repository<Employee, EmployeeId> employeeRepo = createEmployeeRepository();

        //when
        Optional<Employee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        Employee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.name()).isEqualTo("Bruce");
        assertThat(employee.email().value()).isEqualTo("bruce@payroll.com");
        assertThat(employee.isHourly()).isTrue();
        assertThat(employee.isMale()).isTrue();
        assertThat(employee.address()).isEqualTo(new Address("China", "SiChuan", "chengdu", "qingyang avenue", "600000"));
        assertThat(employee.contact()).isEqualTo(Contact.of("15028150000"));
        assertThat(employee.contact().homePhone()).isNull();
        assertThat(employee.boardingDate()).isEqualTo(LocalDate.of(2001, 9, 10));
    }

    @Test
    public void should_query_hourly_employee_and_related_timecards_by_id() {
        //given
        String employeeId = "emp200109101000001";
        Repository<HourlyEmployee, EmployeeId> employeeRepo = createHourlyEmployeeRepository();

        //when
        Optional<HourlyEmployee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        HourlyEmployee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.salaryOfHour()).isEqualTo(Salary.of(100.00));

        List<TimeCard> timeCards = employee.timeCards();
        assertThat(timeCards)
                .isNotNull()
                .hasSize(5);

        TimeCard timeCard = timeCards.get(0);
        assertThat(timeCard.workHours()).isEqualTo(8);
        assertThat(timeCard.getRegularWorkHours()).isEqualTo(8);
        assertThat(timeCard.getOvertimeWorkHours()).isEqualTo(0);
        assertThat(timeCard.isOvertime()).isFalse();
    }

    @Test
    public void should_query_salaried_employee_and_related_absences_by_id() {
        //given
        String employeeId = "emp201110101000003";
        Repository<SalariedEmployee, EmployeeId> employeeRepo = createSalariedEmployeeRepository();

        //when
        Optional<SalariedEmployee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        SalariedEmployee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.salaryOfMonth()).isEqualTo(Salary.of(10000.00));

        List<Absence> absences = employee.absences();
        assertThat(absences).isNotNull().hasSize(4);

        Absence absence = absences.get(0);
        assertThat(absence.isPaidLeave()).isFalse();
    }

    @Test
    public void should_get_all_entities() {
        //given
        Repository<Employee, EmployeeId> repository = createEmployeeRepository();

        //when
        List<Employee> employees = repository.findAll();

        //then
        assertThat(employees).isNotNull().hasSize(5);
    }

    private Repository<SalariedEmployee, EmployeeId> createSalariedEmployeeRepository() {
        return new Repository<>(SalariedEmployee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }

    private Repository<HourlyEmployee, EmployeeId> createHourlyEmployeeRepository() {
        return new Repository<>(HourlyEmployee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }

    private Repository<Employee, EmployeeId> createEmployeeRepository() {
        return new Repository<>(Employee.class, EntityManagers.from(PERSISTENCE_UNIT_NAME));
    }
}