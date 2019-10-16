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

    private static String composeRandomNumber() {
        int length = 6;
        String number = String.valueOf(random.nextInt(999999));

        if (number.length() == length) {
            return number;
        }

        if (number.length() < length) {
            return appendWithZero(number, length);
        }

        return number.substring(0, length);
    }

    private static String appendWithZero(String str, int length) {
        int numberOfZero = length - str.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfZero; i++) {
            builder.append("0");
        }
        builder.append(str);
        return builder.toString();
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
