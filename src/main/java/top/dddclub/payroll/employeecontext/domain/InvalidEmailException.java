package top.dddclub.payroll.employeecontext.domain;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("invalid email address");
    }
}
