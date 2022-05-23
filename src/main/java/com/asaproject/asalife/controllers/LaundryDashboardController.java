package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.responses.CountByMonthDto;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;
import com.asaproject.asalife.services.CateringDashboardService;
import com.asaproject.asalife.services.LaundryDashboardService;
import com.asaproject.asalife.services.MainDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laundry-dashboard")
public class LaundryDashboardController extends HandlerController{
    private final LaundryDashboardService laundryDashboardService;
    private final MainDashboardService mainDashboardService;

    @GetMapping("/widget")
    public ResponseEntity<List<RecordDashboard>> getAllInfoWidget () {
        return ResponseEntity.ok(laundryDashboardService.countEveryStatusLaundry());
    }

    @GetMapping("/count-last-5-month")
    public ResponseEntity<List<CountByMonthDto>> getACountLastMonths () {
        return ResponseEntity.ok(mainDashboardService.getByMonthLaundry());
    }
}
