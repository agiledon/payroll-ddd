package top.dddclub.payroll.employeecontext.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    private String country;
    private String province;
    private String city;
    private String street;
    private String zip;

    public Address() {
    }

    public Address(String country, String province, String city, String street, String zip) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(province, address.province) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(zip, address.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, province, city, street, zip);
    }
}
