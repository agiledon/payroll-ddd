package top.dddclub.payroll.payrollcontext.domain.salariedemployee;

public enum LeaveReason {
    SickLeave(0.5), CasualLeave(0.5), MaternityLeave(0), BereavementLeave(0), DisapprovedLeave(1);

    private double deductionRatio;

    LeaveReason(double deductionRatio) {
        this.deductionRatio = deductionRatio;
    }

    public double deductionRatio() {
        return this.deductionRatio;
    }

    public boolean isPaidLeave() {
        return deductionRatio == 0;
    }
}
