package top.dddclub.payroll.core.gateway.persistence;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.domain.Address;
import top.dddclub.payroll.employeecontext.domain.Contact;
import top.dddclub.payroll.employeecontext.domain.Employee;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryIT {
    @Test
    public void should_query_employee_table_and_get_it_by_id() {
        //given
        Repository<Employee, EmployeeId> employeeRepo = new Repository<>(Employee.class, EntityManagers.from("PAYROLL_JPA"));

        //when
        Optional<Employee> optEmployee = employeeRepo.findById(EmployeeId.of("emp200190101000001"));

        //then
        assertThat(optEmployee.isPresent()).isTrue();

        Employee employee = optEmployee.get();
        assertThat(employee.id().value()).isEqualTo("emp200190101000001");
        assertThat(employee.name()).isEqualTo("Bruce");
        assertThat(employee.email().value()).isEqualTo("bruce@payroll.com");
        assertThat(employee.isHourly()).isTrue();
        assertThat(employee.isMale()).isTrue();
        assertThat(employee.address()).isEqualTo(new Address("China", "SiChuan", "chengdu", "qingyang avenue", "600000"));
        assertThat(employee.contact()).isEqualTo(Contact.of("15028150000"));
        assertThat(employee.contact().homePhone()).isNull();
    }
}