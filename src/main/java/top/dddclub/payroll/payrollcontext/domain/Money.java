package top.dddclub.payroll.payrollcontext.domain;

import top.dddclub.payroll.payrollcontext.domain.exceptions.NotSameCurrencyException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Money {
    private static final int SCALE = 2;

    @Column(name = "salary")
    private BigDecimal value;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Money() {
    }

    public static Money of(double value) {
        return new Money(value, Currency.RMB);
    }

    public static Money of(double value, Currency currency) {
        return new Money(value, currency);
    }

    private Money(double value, Currency currency) {
        this.value = toBigDecimal(value).setScale(SCALE);
        this.currency = currency;
    }

    private Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Currency currency() {
        return this.currency;
    }

    public static Money zero() {
        return zero(Currency.RMB);
    }

    public static Money zero(Currency currency) {
        return new Money(0d, currency);
    }

    public Money add(Money money) {
        throwExceptionIfNotSameCurrency(money);
        return new Money(value.add(money.value).setScale(SCALE), currency);
    }

    public Money subtract(Money money) {
        throwExceptionIfNotSameCurrency(money);
        return new Money(value.subtract(money.value).setScale(SCALE), currency);
    }

    public Money multiply(double factor) {
        return new Money(value.multiply(toBigDecimal(factor)).setScale(SCALE), currency);
    }

    public Money divide(double multiplicand) {
        return new Money(value.divide(toBigDecimal(multiplicand), SCALE, BigDecimal.ROUND_DOWN), currency);
    }

    private void throwExceptionIfNotSameCurrency(Money money) {
        if (money.currency != this.currency) {
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
        Money money = (Money) o;
        return Objects.equals(value, money.value) &&
                currency == money.currency;
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