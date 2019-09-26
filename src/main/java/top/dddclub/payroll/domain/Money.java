package top.dddclub.payroll.domain;

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
}

