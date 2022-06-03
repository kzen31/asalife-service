package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.ApiResponse;
import com.asaproject.asalife.domains.models.responses.MaintenanceDto;
import com.asaproject.asalife.domains.models.responses.TaskMaintenanceDto;
import com.asaproject.asalife.services.MaintenanceService;
import com.asaproject.asalife.services.TaskMaintenanceService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maintenance")
public class MaintenanceController extends HandlerController {
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
    public ResponseEntity<ApiResponse> addOrderMaintenance(Principal principal, @Valid @RequestBody MaintenanceRequest maintenanceRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());
        try {
            maintenanceService.addMaintenance(principal, maintenanceRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Maintenance Order").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<ApiResponse> updateOrderMaintenance(@PathVariable Long id, @Valid @RequestBody MaintenanceOrder maintenanceOrder) {
        try {
            maintenanceService.updateOrder(id, maintenanceOrder);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Order PIC").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update-status-order/{id}")
    public ResponseEntity<ApiResponse> updateStatusOrderMaintenance(@PathVariable Long id, @Valid @RequestBody StatusMaintenance statusMaintenance) {
        try {
            maintenanceService.updateOrderStatus(id, statusMaintenance);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Status Order").build());

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/cancel-order/{id}")
    public ResponseEntity<ApiResponse> cancelOrderMaintenance(@PathVariable Long id) {
        try {
            maintenanceService.cancelOrder(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Status Order").build());

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
    public ResponseEntity<ApiResponse> addTaskMaintenance(Principal principal, @Valid @RequestBody TaskMaintenanceRequest taskMaintenanceRequest){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/task-add").toUriString());
        try {
            taskMaintenanceService.addTask(principal, taskMaintenanceRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Task Maintenance").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/task-info/{id}")
    public ResponseEntity<TaskMaintenanceDto> getInfoTaskMaintenance(@PathVariable Long id) {
        try {
            TaskMaintenanceDto taskMaintenanceDto = taskMaintenanceService.getInfoTask(id);
            return ResponseEntity.ok(taskMaintenanceDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/task-delete/{id}")
    public ResponseEntity<ApiResponse> deleteTaskMaintenance(@PathVariable Long id) {
        try {
            taskMaintenanceService.deleteTask(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Task Maintenance").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/task-update/{id}")
    public ResponseEntity<ApiResponse> updateTaskMaintenance(@PathVariable Long id, @Valid @RequestBody StatusTaskMaintenance statusTaskMaintenance) {
        try {
            taskMaintenanceService.updateStatusTask(id, statusTaskMaintenance.getStatus());
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Task Maintenance").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping ("/delete-record/{id}")
    public ResponseEntity<ApiResponse> deleteRecordMaintenance(@PathVariable Long id) {
        try {
            maintenanceService.deleteMaintenance(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Record Maintenance").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record Maintenance Not Found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not valid", e.getCause());
        }
    }
}
