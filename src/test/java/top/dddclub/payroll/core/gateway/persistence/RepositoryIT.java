package top.dddclub.payroll.core.gateway.persistence;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.domain.Address;
import top.dddclub.payroll.employeecontext.domain.Contact;
import top.dddclub.payroll.employeecontext.domain.Employee;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.payrollcontext.domain.Money;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.TimeCard;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryIT {
    @Test
    public void should_query_employee_table_by_id() {
        //given
        String employeeId = "emp200109101000001";
        Repository<Employee, EmployeeId> employeeRepo = new Repository<>(Employee.class, EntityManagers.from("PAYROLL_JPA"));

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
        Repository<HourlyEmployee, EmployeeId> employeeRepo = new Repository<>(HourlyEmployee.class, EntityManagers.from("PAYROLL_JPA"));

        //when
        Optional<HourlyEmployee> optEmployee = employeeRepo.findById(EmployeeId.of(employeeId));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        HourlyEmployee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo(employeeId);
        assertThat(employee.salaryOfHour()).isEqualTo(Money.of(100.00));

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
}