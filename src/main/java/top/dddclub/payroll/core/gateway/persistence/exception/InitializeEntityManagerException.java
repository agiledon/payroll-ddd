package top.dddclub.payroll.core.gateway.persistence.exception;

public class InitializeEntityManagerException extends RuntimeException {
    public InitializeEntityManagerException() {
        super("Failed to initialize Entity Manager.");
    }

    public InitializeEntityManagerException(String message) {
        super(message);
    }
}
