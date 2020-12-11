package top.dddclub.payroll.employeecontext.domain;

import org.junit.Before;
import org.junit.Test;
import top.dddclub.payroll.employeecontext.port.LeaveRepository;
import top.dddclub.payroll.employeecontext.port.PunchingRecordRepository;
import top.dddclub.payroll.fixture.AttendanceFixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AttendanceStatusServiceTest {
    private String employeeId;
    private LocalDate yesterday;
    private AttendanceStatusService service;
    private WorkHour workHour;
    private LeaveRepository mockLeaveRepo;
    private PunchingRecordRepository mockPunchingRepo;

    @Before
    public void setUp() throws Exception {
        employeeId = "emp0001";
        yesterday = LocalDate.of(2020, 12, 11);
        workHour = AttendanceFixture.createWorkHour();
        mockLeaveRepo = mock(LeaveRepository.class);
        mockPunchingRepo = mock(PunchingRecordRepository.class);
    }

    @Test
    public void should_be_Leave_if_leave_is_existed() {
        // given
        when(mockLeaveRepo.leaveFor(employeeId, yesterday)).thenReturn(true);
        service = new AttendanceStatusService(mockLeaveRepo, null);

        // when
        AttendanceStatus status = service.acquireStatus(employeeId, yesterday, workHour);

        // then
        verify(mockLeaveRepo).leaveFor(employeeId, yesterday);
        assertThat(status.isLeave()).isTrue();
    }

    @Test
    public void should_be_Normal_if_leave_is_not_existed_and_punching_record_existed() {
        // given
        when(mockLeaveRepo.leaveFor(employeeId, yesterday)).thenReturn(false);

        PunchingRecord punchingRecord = createPunchingRecord();
        when(mockPunchingRepo.punchingRecordsOf(employeeId, yesterday)).thenReturn(Optional.of(punchingRecord));

        service = new AttendanceStatusService(mockLeaveRepo, mockPunchingRepo);

        // when
        AttendanceStatus status = service.acquireStatus(employeeId, yesterday, workHour);

        // then
        verify(mockLeaveRepo).leaveFor(employeeId, yesterday);
        verify(mockPunchingRepo).punchingRecordsOf(employeeId, yesterday);
        assertThat(status.isNormal()).isTrue();
    }

    private PunchingRecord createPunchingRecord() {
        LocalDateTime arrivedTime = LocalDateTime.of(2020, 12, 11, 9, 0, 0);
        LocalDateTime leaveTime = LocalDateTime.of(2020, 12, 11, 17, 0, 0);
        return new PunchingRecord(employeeId, arrivedTime, leaveTime, yesterday);
    }
}
