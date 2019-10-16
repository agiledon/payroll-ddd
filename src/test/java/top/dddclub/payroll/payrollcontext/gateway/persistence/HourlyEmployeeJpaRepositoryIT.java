package top.dddclub.payroll.payrollcontext.gateway.persistence;

import org.junit.Before;
import org.junit.Test;
import top.dddclub.payroll.core.gateway.persistence.Repository;
import top.dddclub.payroll.employeecontext.domain.EmployeeId;
import top.dddclub.payroll.fixture.EntityManagerFixture;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.HourlyEmployee;
import top.dddclub.payroll.payrollcontext.domain.hourlyemployee.TimeCard;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class HourlyEmployeeJpaRepositoryIT {
    private EntityManager entityManager;
    private Repository<HourlyEmployee, EmployeeId> repository;
    private HourlyEmployeeJpaRepository employeeRepo;

    @Before
    public void setUp() {
        entityManager = EntityManagerFixture.createEntityManager();
        repository = new Repository<>(HourlyEmployee.class, entityManager);
        employeeRepo = new HourlyEmployeeJpaRepository(repository);
    }

    @Test
    public void should_submit_timecard_then_remove_it() {
        EmployeeId employeeId = EmployeeId.of("emp200109101000001");

        HourlyEmployee hourlyEmployee = employeeRepo.employeeOf(employeeId).get();

        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        TimeCard repeatedCard = new TimeCard(LocalDate.of(2019, 9, 2), 8);
        hourlyEmployee.submit(repeatedCard);
        employeeRepo.save(hourlyEmployee);

        hourlyEmployee = employeeRepo.employeeOf(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(5);

        TimeCard submittedCard = new TimeCard(LocalDate.of(2019, 10, 8), 8);
        hourlyEmployee.submit(submittedCard);
        employeeRepo.save(hourlyEmployee);

        hourlyEmployee = employeeRepo.employeeOf(employeeId).get();
        assertThat(hourlyEmployee).isNotNull();
        assertThat(hourlyEmployee.timeCards()).hasSize(6);

        hourlyEmployee.remove(submittedCard);
        employeeRepo.save(hourlyEmployee);
        assertThat(hourlyEmployee.timeCards()).hasSize(5);
    }
}