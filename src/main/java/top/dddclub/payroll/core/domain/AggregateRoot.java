package top.dddclub.payroll.core.domain;

public interface AggregateRoot<R extends AbstractEntity> {
    R root();
}
