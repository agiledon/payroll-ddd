package top.dddclub.payroll.employeecontext.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public String value() {
        return this.value;
    }

    public Email() {
    }

    public Email(String value) {
        this.value = value;
    }
}
