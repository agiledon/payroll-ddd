package top.dddclub.payroll.employeecontext.domain;

import top.dddclub.payroll.core.domain.Identity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

@Embeddable
public class EmployeeId implements Identity<String>, Serializable {
    @Column(name = "id")
    private String value;

    private static Random random;

    static {
        random = new Random();
    }

    public EmployeeId() {
    }

    private EmployeeId(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }

    public static EmployeeId of(String value) {
        return new EmployeeId(value);
    }

    public static Identity<String> next() {
        return new EmployeeId(String.format("%s%s%s",
                        composePrefix(),
                        composeTimestamp(),
                        composeRandomNumber()));
    }

    private static int composeRandomNumber() {
        return random.nextInt(999999);
    }

    private static String composeTimestamp() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static String composePrefix() {
        return "emp";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeId that = (EmployeeId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
