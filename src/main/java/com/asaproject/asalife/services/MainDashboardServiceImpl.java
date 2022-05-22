package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import com.asaproject.asalife.domains.models.responses.CountByStatus;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.domains.models.responses.ReportCountByMonth;
import com.asaproject.asalife.repositories.*;
import com.asaproject.asalife.utils.mappers.CountByMonthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MainDashboardServiceImpl implements MainDashboardService {
    private final CateringRepository cateringRepository;
    private final LaundryRepository laundryRepository;
    private final HousekeepingRepository housekeepingRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final CountByMonthMapper countByMonthMapper;


    @Override
    public List<RecordDashboard> getTotalRecord() {
        List<RecordDashboard> recordDashboards = new ArrayList<>();
        recordDashboards.add(objectTotalRecordCatering());
        recordDashboards.add(objectTotalRecordLaundry());
        recordDashboards.add(objectTotalRecordHousekeeping());
        recordDashboards.add(objectTotalRecordMaintenance());
        return recordDashboards;
    }

    @Override
    public RecordDashboard objectTotalRecordLaundry() {
        RecordDashboard recordLaundry = new RecordDashboard();
        recordLaundry.setTitle("Laundry");
        recordLaundry.setIconClass("water-check");
        recordLaundry.setTotal(getTotalRecordLaundry());
        return recordLaundry;
    }

    @Override
    public Long getTotalRecordLaundry() {
        return laundryRepository.count();
    }

    @Override
    public RecordDashboard objectTotalRecordCatering() {
        RecordDashboard recordCatering = new RecordDashboard();
        recordCatering.setTitle("Catering");
        recordCatering.setIconClass("food-fork-drink");
        recordCatering.setTotal(getTotalRecordCatering());
        return recordCatering;
    }

    @Override
    public Long getTotalRecordCatering() {
        return cateringRepository.count();
    }

    @Override
    public RecordDashboard objectTotalRecordMaintenance() {
        RecordDashboard recordMaintenance = new RecordDashboard();
        recordMaintenance.setTitle("Maintenance");
        recordMaintenance.setIconClass("tools");
        recordMaintenance.setTotal(getTotalRecordMaintenance());
        return recordMaintenance;
    }

    @Override
    public Long getTotalRecordMaintenance() {
        return maintenanceRepository.count();
    }

    @Override
    public RecordDashboard objectTotalRecordHousekeeping() {
        RecordDashboard recordHousekeeping = new RecordDashboard();
        recordHousekeeping.setTitle("Housekeeping");
        recordHousekeeping.setIconClass("hand-heart");
        recordHousekeeping.setTotal(getTotalRecordHousekeeping());
        return recordHousekeeping;
    }

    @Override
    public Long getTotalRecordHousekeeping() {
        return housekeepingRepository.count();
    }

    @Override
    public List<CountByStatus> getCountClosedOrOngoing() {
        List<CountByStatus> countByStatusList = new ArrayList<>();
        countByStatusList.add(getCountOngoing());
        countByStatusList.add(getCountClosed());
        return countByStatusList;
    }

    @Override
    public CountByStatus getCountClosed() {
        Long countCateringClosed = cateringRepository.countClosed();
        Long countLaundryClosed = laundryRepository.countClosed();
        Long countHousekeepingClosed = housekeepingRepository.countClosed();
        Long countMaintenanceClosed = maintenanceRepository.countClosed();

        Long total = countCateringClosed + countLaundryClosed + countHousekeepingClosed + countMaintenanceClosed;

        CountByStatus countClosed = new CountByStatus();
        countClosed.setStatus("CLOSED");
        countClosed.setCount(total);
        return countClosed;
    }

    @Override
    public CountByStatus getCountOngoing() {
        Long countCateringOngoing = cateringRepository.countOngoing();
        Long countLaundryOngoing = laundryRepository.countOngoing();
        Long countHousekeepingOngoing = housekeepingRepository.countOngoing();
        Long countMaintenanceOngoing = maintenanceRepository.countOngoing();

        Long total = countCateringOngoing + countLaundryOngoing + countHousekeepingOngoing + countMaintenanceOngoing;

        CountByStatus countOngoing = new CountByStatus();
        countOngoing.setStatus("ONGOING");
        countOngoing.setCount(total);
        return countOngoing;
    }

    @Override
    public List<CountByMonthDto> getByMonthCatering() {
        List<CountByMonth> countByMonthList = cateringRepository.countByMonth();
        return countByMonthMapper.createListCountByMonthDto(countByMonthList);
    }

    @Override
    public ReportCountByMonth getByMonthCateringData() {
        ReportCountByMonth report = new ReportCountByMonth();
        report.setAduan("Catering");
        report.setData(getByMonthCatering());
        return report;
    }

    @Override
    public List<CountByMonthDto> getByMonthLaundry() {
        List<CountByMonth> countByMonthList = laundryRepository.countByMonth();
        return countByMonthMapper.createListCountByMonthDto(countByMonthList);
    }

    @Override
    public ReportCountByMonth getByMonthLaundryData() {
        ReportCountByMonth report = new ReportCountByMonth();
        report.setAduan("Laundry");
        report.setData(getByMonthLaundry());
        return report;
    }

    @Override
    public List<CountByMonthDto> getByMonthHousekeeping() {
        List<CountByMonth> countByMonthList = housekeepingRepository.countByMonth();
        return countByMonthMapper.createListCountByMonthDto(countByMonthList);
    }

    @Override
    public ReportCountByMonth getByMonthHousekeepingData() {
        ReportCountByMonth report = new ReportCountByMonth();
        report.setAduan("Housekeeping");
        report.setData(getByMonthHousekeeping());
        return report;
    }

    @Override
    public List<CountByMonthDto> getByMonthMaintenance() {
        List<CountByMonth> countByMonthList = maintenanceRepository.countByMonth();
        return countByMonthMapper.createListCountByMonthDto(countByMonthList);
    }

    @Autowired
    public ReportCountByMonth getByMonthMaintenanceData() {
        ReportCountByMonth report = new ReportCountByMonth();
        report.setAduan("Maintenance");
        report.setData(getByMonthMaintenance());
        return report;
    }

    @Autowired
    public List<ReportCountByMonth> getReportCountByMonthAduan() {
        List<ReportCountByMonth> report = new ArrayList<>();
        report.add(getByMonthCateringData());
        report.add(getByMonthLaundryData());
        report.add(getByMonthHousekeepingData());
        report.add(getByMonthMaintenanceData());
        return report;
    }
}
