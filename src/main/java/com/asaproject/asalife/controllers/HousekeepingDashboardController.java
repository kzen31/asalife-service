package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.services.CateringDashboardService;
import com.asaproject.asalife.services.HousekeepingDashboardService;
import com.asaproject.asalife.services.MainDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/housekeeping-dashboard")
public class HousekeepingDashboardController extends HandlerController{
    private final HousekeepingDashboardService housekeepingDashboardService;
    private final MainDashboardService mainDashboardService;

    @GetMapping("/widget")
    public ResponseEntity<List<RecordDashboard>> getAllInfoWidget () {
        return ResponseEntity.ok(housekeepingDashboardService.countEveryStatusHousekeeping());
    }

    @GetMapping("/count-last-5-month")
    public ResponseEntity<List<CountByMonthDto>> getACountLastMonths () {
        return ResponseEntity.ok(mainDashboardService.getByMonthHousekeeping());
    }
}
