package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.requests.HousekeepingRequest;
import com.asaproject.asalife.domains.models.requests.StatusHousekeeping;
import com.asaproject.asalife.domains.models.responses.HousekeepingDto;
import com.asaproject.asalife.services.HousekeepingService;
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

}
