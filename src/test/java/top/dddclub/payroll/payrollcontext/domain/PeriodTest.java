package top.dddclub.payroll.payrollcontext.domain;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodTest {
    @Test
    public void should_be_last_day_given_correct_year_and_month() {
        Period period = new Period(2019, 2);

        assertThat(period.beginDate()).isEqualTo(LocalDate.of(2019, 2, 1));
        assertThat(period.endDate()).isEqualTo(LocalDate.of(2019, 2, 28));
    }

    @Test
    public void should_throw_exception_given_invalid_month_number() {
        assertThatThrownBy(() -> new Period(2019, 13)).hasMessage("Invalid month value.");
    }
}