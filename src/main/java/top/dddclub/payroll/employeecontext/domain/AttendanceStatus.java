package top.dddclub.payroll.employeecontext.domain;

public enum AttendanceStatus {
    Normal, Late, Leave;

    public boolean isNormal() {
        return this == Normal;
    }

    public boolean isLate() {
        return this == Late;
    }

    public boolean isLeave() {
        return this == Leave;
    }
}
