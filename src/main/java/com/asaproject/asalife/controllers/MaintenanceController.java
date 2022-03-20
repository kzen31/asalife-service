package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;
import com.asaproject.asalife.domains.models.responses.TaskMaintenanceDto;
import com.asaproject.asalife.services.MaintenanceService;
import com.asaproject.asalife.services.TaskMaintenanceService;
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
    private final TaskMaintenanceService taskMaintenanceService;

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceDto>> getAllMaintenance() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenance());
    }

    @GetMapping("/my")
    public ResponseEntity<List<MaintenanceDto>> getMyMaintenance(Principal principal) {
        return ResponseEntity.ok(maintenanceService.getAllUserMaintenance(principal));
    }

    @GetMapping("/my-task")
    public ResponseEntity<List<MaintenanceDto>> getMyOrderMaintenance(Principal principal) {
        return ResponseEntity.ok(maintenanceService.getAllPicMaintenance(principal));
    }

    @PostMapping("/add")
    public ResponseEntity<MaintenanceDto> addOrderMaintenance(Principal principal, @RequestBody MaintenanceRequest maintenanceRequest) {
        return ResponseEntity.ok(maintenanceService.addMaintenance(principal, maintenanceRequest));
    }

    @PutMapping("/update-order")
    public ResponseEntity<MaintenanceDto> updateOrderMaintenance(Long id, @RequestBody MaintenanceOrder maintenanceOrder) {
        try {
            MaintenanceDto maintenanceDto = maintenanceService.updateOrder(id, maintenanceOrder);
            return ResponseEntity.ok(maintenanceDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update-status-order")
    public ResponseEntity<MaintenanceDto> updateStatusOrderMaintenance(Long id, @RequestBody StatusMaintenance statusMaintenance) {
        try {
            MaintenanceDto maintenanceDto = maintenanceService.updateOrderStatus(id, statusMaintenance);
            return ResponseEntity.ok(maintenanceDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/cancel-order")
    public ResponseEntity<String> cancelOrderMaintenance(Long id) {
        try {
            maintenanceService.cancelOrder(id);
            return ResponseEntity.ok("Success");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskMaintenanceDto>> getAllTaskMaintenance () {
        return ResponseEntity.ok(taskMaintenanceService.getAllTask());
    }

    @GetMapping("/task-my")
    public ResponseEntity<List<TaskMaintenanceDto>> getMyTaskMaintenance (Principal principal) {
        return ResponseEntity.ok(taskMaintenanceService.getMyTask(principal));
    }

    @PostMapping("/task-add")
    public ResponseEntity<List<TaskMaintenanceDto>> addTaskMaintenance(Principal principal, @RequestBody TaskMaintenanceRequest taskMaintenanceRequest){
        List<TaskMaintenanceDto> taskMaintenanceDto = taskMaintenanceService.addTask(principal, taskMaintenanceRequest);
        return ResponseEntity.ok(taskMaintenanceDto);
    }

    @GetMapping("/task-info")
    public ResponseEntity<TaskMaintenanceDto> getInfoTaskMaintenance(Long id) {
        try {
            TaskMaintenanceDto taskMaintenanceDto = taskMaintenanceService.getInfoTask(id);
            return ResponseEntity.ok(taskMaintenanceDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/task-delete")
    public ResponseEntity<String> deleteTaskMaintenance(Long id) {
        try {
            taskMaintenanceService.deleteTask(id);
            return ResponseEntity.ok("Success");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/task-update")
    public ResponseEntity<TaskMaintenanceDto> updateTaskMaintenance(Long id, @RequestBody StatusTaskMaintenance statusTaskMaintenance) {
        try {
            TaskMaintenanceDto taskMaintenanceDto = taskMaintenanceService.updateStatusTask(id, statusTaskMaintenance.getStatus());
            return ResponseEntity.ok(taskMaintenanceDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
