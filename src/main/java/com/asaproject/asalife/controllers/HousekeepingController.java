package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.models.requests.HousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.RecordHousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.StatusHousekeeping;
import com.asaproject.asalife.domains.models.responses.*;
import com.asaproject.asalife.services.HousekeepingService;
import com.asaproject.asalife.services.RecordHousekeepingService;
import com.asaproject.asalife.services.RuangDetailService;
import com.asaproject.asalife.services.RuangService;
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
@RequestMapping("/api/housekeeping")
public class HousekeepingController extends HandlerController {
    private final HousekeepingService housekeepingService;
    private final RuangService ruangService;
    private final RuangDetailService ruangDetailService;
    private final RecordHousekeepingService recordHousekeepingService;

    @GetMapping("/all")
    public ResponseEntity<List<HousekeepingDto>> getAllHousekeeping () {
        return ResponseEntity.ok(housekeepingService.getAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<HousekeepingDto>> getAllUserHousekeeping (Principal principal) {
        return ResponseEntity.ok(housekeepingService.getAllByUser(principal));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addHousekeepingByUser(Principal principal, @Valid @RequestBody HousekeepingRequest housekeepingRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());
        try {
            housekeepingService.addByUser(principal, housekeepingRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Aduan HouseKeeping").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStatusHousekeeping(@PathVariable Long id, @Valid @RequestBody StatusHousekeeping statusHousekeeping){
        try {
            housekeepingService.updateStatusHousekeeping(id, statusHousekeeping);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Aduan Housekeeping").build());

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/ruang")
    public ResponseEntity<List<RuangDto>> getAllRuang() {
        return ResponseEntity.ok(ruangService.getAllRuang());
    }

    @GetMapping("/ruang-detail/{id}")
    public ResponseEntity<List<RuangDetailDto>> getAllRuangDetail(@PathVariable Long id) {
        try {
            List<RuangDetailDto> ruangDetailList = ruangDetailService.getAllRuangDetail(id);
            return ResponseEntity.ok(ruangDetailList);
        } catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/ruang-detail-all")
    public ResponseEntity<List<RuangDetailDto>> getAllRuangDetail() {
        return ResponseEntity.ok(ruangDetailService.getAllRuangDetail());
    }

    @GetMapping("/record")
    public ResponseEntity<List<RecordResponse>> getALlRecord() {
        return ResponseEntity.ok(recordHousekeepingService.getAllRecord());
    }

    @GetMapping("/record-my")
    public ResponseEntity<List<RecordResponse>> getALlMyRecord(Principal principal) {
        return ResponseEntity.ok(recordHousekeepingService.getMyRecord(principal));
    }

    @GetMapping("/record-user")
    public ResponseEntity<List<RecordResponse>> getALlUserRecord(String nrp) {
        try {
            return ResponseEntity.ok(recordHousekeepingService.getAllByUser(nrp));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/record-add/{id}")
    public ResponseEntity<ApiResponse> addUserRecord(Principal principal, @PathVariable Long id, @Valid @RequestBody RecordHousekeepingRequest recordRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/record-add/{id}").toUriString());
        try {
            recordHousekeepingService.addRecord(principal, id, recordRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Record").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/record-update/{id}")
    public ResponseEntity<ApiResponse> updateCeklisRecord(@PathVariable Long id, @Valid @RequestBody RecordHousekeepingRequest request) {
        try {
            recordHousekeepingService.verifyRecordStatus(id, request);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Record").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping ("/delete-record/{id}")
    public ResponseEntity<ApiResponse> deleteRecordHousekeeping(@PathVariable Long id) {
        try {
            housekeepingService.deleteHousekeeping(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Record Housekeeping").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record Housekeeping Not Found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not valid", e.getCause());
        }
    }
}
