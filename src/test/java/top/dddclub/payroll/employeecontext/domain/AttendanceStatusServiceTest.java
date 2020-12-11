package top.dddclub.payroll.employeecontext.domain;

import org.junit.Test;
import top.dddclub.payroll.employeecontext.port.LeaveRepository;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AttendanceStatusServiceTest {
    @Test
    public void should_be_Leave_if_leave_is_existed() {
        // given
        String employeeId = "emp0001";
        LocalDate yesterday = LocalDate.of(2020, 12, 11);

        LeaveRepository mockLeaveRepo = mock(LeaveRepository.class);
        when(mockLeaveRepo.leaveFor(employeeId, yesterday)).thenReturn(true);

        AttendanceStatusService service = new AttendanceStatusService(mockLeaveRepo);

        // when
        AttendanceStatus status = service.acquireStatus(employeeId, yesterday);

        // then
        verify(mockLeaveRepo).leaveFor(employeeId, yesterday);
        assertThat(status.isLeave()).isTrue();
    }
}
