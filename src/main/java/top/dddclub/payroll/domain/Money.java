package top.dddclub.payroll.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private static final int SCALE = 2;
    private final BigDecimal value;
    private final Currency currency;

    public static Money of(double value, Currency currency) {
        return new Money(value, currency);
    }

    private Money(double value, Currency currency) {
        this.value = new BigDecimal(Double.toString(value)).setScale(SCALE);
        this.currency = currency;
    }

    private Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money multiply(double factor) {
        BigDecimal factorDecimal = new BigDecimal(Double.toString(factor));
        return new Money(value.multiply(factorDecimal).setScale(SCALE), currency);
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