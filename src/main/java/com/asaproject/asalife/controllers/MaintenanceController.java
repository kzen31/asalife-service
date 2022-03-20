package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.Maintenance;
import com.asaproject.asalife.domains.models.requests.MaintenanceOrder;
import com.asaproject.asalife.domains.models.requests.MaintenanceRequest;
import com.asaproject.asalife.domains.models.requests.StatusMaintenance;
import com.asaproject.asalife.services.MaintenanceService;
import javassist.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/all")
    public ResponseEntity<List<Maintenance>> getAllMaintenance() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenance());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Maintenance>> getMyMaintenance(Principal principal) {
        return ResponseEntity.ok(maintenanceService.getAllUserMaintenance(principal));
    }

    @GetMapping("/my-task")
    public ResponseEntity<List<Maintenance>> getMyOrderMaintenance(Principal principal) {
        return ResponseEntity.ok(maintenanceService.getAllPicMaintenance(principal));
    }

    @PostMapping("/add")
    public ResponseEntity<Maintenance> addOrderMaintenance(Principal principal, @RequestBody MaintenanceRequest maintenanceRequest) {
        return ResponseEntity.ok(maintenanceService.addMaintenance(principal, maintenanceRequest));
    }

    @PutMapping("/update-order")
    public ResponseEntity<Maintenance> updateOrderMaintenance(Long id, @RequestBody MaintenanceOrder maintenanceOrder) {
        try {
            Maintenance maintenance = maintenanceService.updateOrder(id, maintenanceOrder);
            return ResponseEntity.ok(maintenance);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update-status-order")
    public ResponseEntity<Maintenance> updateStatusOrderMaintenance(Long id, @RequestBody StatusMaintenance statusMaintenance) {
        try {
            Maintenance maintenance = maintenanceService.updateOrderStatus(id, statusMaintenance);
            return ResponseEntity.ok(maintenance);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/cancel-order")
    public ResponseEntity<Maintenance> cancelOrderMaintenance(Long id) {
        try {
            Maintenance maintenance = maintenanceService.cancelOrder(id);
            return ResponseEntity.ok(maintenance);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
