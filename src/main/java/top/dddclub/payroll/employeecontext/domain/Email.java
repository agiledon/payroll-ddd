package top.dddclub.payroll.employeecontext.domain;

import antlr.StringUtils;

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
        if (value == null || value.equals("")) {
            throw new InvalidEmailException();
        }

        this.value = value;
    }
}
