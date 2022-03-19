package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.models.requests.HousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.RecordHousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.StatusHousekeeping;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordHousekeepingDto;
import com.asaproject.asalife.domains.models.responses.RecordResponse;
import com.asaproject.asalife.domains.models.responses.RuangDetailDto;
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

import java.security.Principal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/housekeeping")
public class HousekeepingController {
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
    public ResponseEntity<String> addHousekeepingByUser(Principal principal, @RequestBody HousekeepingRequest housekeepingRequest) {
        try {
            housekeepingService.addByUser(principal, housekeepingRequest);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<List<HousekeepingDto>> updateStatusHousekeeping(Long id, @RequestBody StatusHousekeeping statusHousekeeping){
        try {
            List<HousekeepingDto> housekeepingDtoList = housekeepingService.updateStatusHousekeeping(id, statusHousekeeping);
            return ResponseEntity.ok(housekeepingDtoList);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/ruang")
    public ResponseEntity<List<Ruang>> getAllRuang() {
        return ResponseEntity.ok(ruangService.getAllRuang());
    }

    @GetMapping("/ruang-detail")
    public ResponseEntity<List<RuangDetailDto>> getAllRuangDetail(Long id) {
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

    @PostMapping("/record-add")
    public ResponseEntity<String> addUserRecord(Principal principal, Long id, @RequestBody RecordHousekeepingRequest recordRequest) {
        try {
            recordHousekeepingService.addRecord(principal, id, recordRequest);
            return ResponseEntity.ok("Succes");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/record-update")
    public ResponseEntity<String> updateCeklisRecord(Long id, @RequestBody RecordHousekeepingRequest request) {
        try {
            recordHousekeepingService.verifyRecordStatus(id, request);
            return ResponseEntity.ok("Sucess");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
