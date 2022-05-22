package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import com.asaproject.asalife.domains.models.responses.CountByStatus;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.domains.models.responses.ReportCountByMonth;

import java.util.List;

public interface MainDashboardService {
    List<RecordDashboard> getTotalRecord();

    RecordDashboard objectTotalRecordLaundry();
    Long getTotalRecordLaundry();

    RecordDashboard objectTotalRecordCatering();
    Long getTotalRecordCatering();

    RecordDashboard objectTotalRecordMaintenance();
    Long getTotalRecordMaintenance();

    RecordDashboard objectTotalRecordHousekeeping();
    Long getTotalRecordHousekeeping();

    List<CountByStatus> getCountClosedOrOngoing();

    CountByStatus getCountClosed();

    CountByStatus getCountOngoing();

    List<CountByMonthDto> getByMonthCatering();
    List<CountByMonthDto> getByMonthLaundry();
    List<CountByMonthDto> getByMonthHousekeeping();
    List<CountByMonthDto> getByMonthMaintenance();
    ReportCountByMonth getByMonthCateringData();
    ReportCountByMonth getByMonthLaundryData();
    ReportCountByMonth getByMonthHousekeepingData();
    ReportCountByMonth getByMonthMaintenanceData();
    List<ReportCountByMonth> getReportCountByMonthAduan();
}
