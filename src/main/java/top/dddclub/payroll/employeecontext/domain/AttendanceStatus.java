package top.dddclub.payroll.employeecontext.domain;

public enum AttendanceStatus {
    Normal, Late;

    public boolean isNormal() {
        return this == Normal;
    }

    public boolean isLate() {
        return this == Late;
    }
}
