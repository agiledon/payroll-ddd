package top.dddclub.payroll.employeecontext.domain;

public enum AttendanceStatus {
    Normal;

    public boolean isNormal() {
        return this == Normal;
    }
}
