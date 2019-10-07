package top.dddclub.payroll.employeecontext.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Contact {
    private String mobilePhone;
    private String homePhone;
    private String officePhone;

    public Contact() {
    }

    private Contact(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public static Contact of(String mobilePhone) {
        return new Contact(mobilePhone);
    }

    public Contact withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public Contact withOfficePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public String mobilePhone() {
        return this.mobilePhone;
    }

    public String homePhone() {
        return this.homePhone;
    }

    public String officePhone() {
        return this.officePhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(mobilePhone, contact.mobilePhone) &&
                Objects.equals(homePhone, contact.homePhone) &&
                Objects.equals(officePhone, contact.officePhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobilePhone, homePhone, officePhone);
    }
}
