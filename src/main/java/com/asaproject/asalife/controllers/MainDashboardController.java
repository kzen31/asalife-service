package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.interfaces.CountByMonth;
import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import com.asaproject.asalife.domains.models.responses.CountByStatus;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.domains.models.responses.ReportCountByMonth;
import com.asaproject.asalife.services.MainDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main-dashboard")
public class MainDashboardController extends HandlerController{
    private final MainDashboardService mainDashboardService;

    @GetMapping("/widget")
    public ResponseEntity<List<RecordDashboard>> getAllInfoWidget () {
        return ResponseEntity.ok(mainDashboardService.getTotalRecord());
    }

    @GetMapping("/info-by-status")
    public ResponseEntity<List<CountByStatus>> getAllInfoByStatusComplaint () {
        return ResponseEntity.ok(mainDashboardService.getCountClosedOrOngoing());
    }

    @GetMapping("/count-last-5-month")
    public ResponseEntity<List<ReportCountByMonth>> getACountLastMonths () {
        return ResponseEntity.ok(mainDashboardService.getReportCountByMonthAduan());
    }
}
