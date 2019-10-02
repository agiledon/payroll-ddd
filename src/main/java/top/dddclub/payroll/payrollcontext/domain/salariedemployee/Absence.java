package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

import java.time.LocalDate;

public class Absence {
    private String employeeId;
    private LocalDate leaveDate;
    private LeaveReason leaveReason;

    public Absence(String employeeId, LocalDate leaveDate, LeaveReason leaveReason) {
        this.employeeId = employeeId;
        this.leaveDate = leaveDate;
        this.leaveReason = leaveReason;
    }

    public boolean isPaidLeave() {
        return leaveReason.isPaidLeave();
    }

    public double deductionRatio() {
        return leaveReason.deductionRatio();
    }
}
