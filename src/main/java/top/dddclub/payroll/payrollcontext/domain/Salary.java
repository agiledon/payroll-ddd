package top.dddclub.payroll.payrollcontext.domain;

import top.dddclub.payroll.payrollcontext.domain.exceptions.NotSameCurrencyException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Salary {
    private static final int SCALE = 2;

    @Column(name = "salary")
    private BigDecimal value;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Salary() {
    }

    public static Salary of(double value) {
        return new Salary(value, Currency.RMB);
    }

    public static Salary of(double value, Currency currency) {
        return new Salary(value, currency);
    }

    private Salary(double value, Currency currency) {
        this.value = toBigDecimal(value).setScale(SCALE);
        this.currency = currency;
    }

    private Salary(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Currency currency() {
        return this.currency;
    }

    public static Salary zero() {
        return zero(Currency.RMB);
    }

    public static Salary zero(Currency currency) {
        return new Salary(0d, currency);
    }

    public Salary add(Salary salary) {
        throwExceptionIfNotSameCurrency(salary);
        return new Salary(value.add(salary.value).setScale(SCALE), currency);
    }

    public Salary subtract(Salary salary) {
        throwExceptionIfNotSameCurrency(salary);
        return new Salary(value.subtract(salary.value).setScale(SCALE), currency);
    }

    public Salary multiply(double factor) {
        return new Salary(value.multiply(toBigDecimal(factor)).setScale(SCALE), currency);
    }

    public Salary divide(double multiplicand) {
        return new Salary(value.divide(toBigDecimal(multiplicand), SCALE, BigDecimal.ROUND_DOWN), currency);
    }

    private void throwExceptionIfNotSameCurrency(Salary salary) {
        if (salary.currency != this.currency) {
            throw new NotSameCurrencyException("Don't support different currency.");
        }
    }

    private BigDecimal toBigDecimal(double factor) {
        return new BigDecimal(Double.toString(factor));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary = (Salary) o;
        return Objects.equals(value, salary.value) &&
                currency == salary.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Money {" +
                "face value = " + value +
                ", currency = " + currency +
                '}';
    }
}