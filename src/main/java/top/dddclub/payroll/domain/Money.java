package top.dddclub.payroll.domain;

import java.util.Objects;

public class Money {
    private final long value;
    private final Currency currency;

    public static Money of(long value, Currency currency) {
        return new Money(value, currency);
    }

    private Money(long value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money multiple(int factor) {
        return new Money(value * factor, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}