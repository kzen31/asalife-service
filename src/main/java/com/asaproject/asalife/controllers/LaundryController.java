package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.requests.LaundryRequest;
import com.asaproject.asalife.domains.models.requests.StatusLaundry;
import com.asaproject.asalife.domains.models.responses.LaundryDto;
import com.asaproject.asalife.services.LaundryService;
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
@RequestMapping("/laundry")
public class LaundryController {
    private final LaundryService laundryService;

    @GetMapping("/all")
    public ResponseEntity<List<LaundryDto>> getAllLaundry () {
        return ResponseEntity.ok(laundryService.getAllLaundryDto());
    }

    @GetMapping("/my")
    public ResponseEntity<List<LaundryDto>> getAllUserLaundry (Principal principal) {
        return ResponseEntity.ok(laundryService.getAllLaundryByUserDto(principal));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLaundryByUser(Principal principal, @RequestBody LaundryRequest laundryRequest) {
        try {
            laundryService.addLaundry(principal, laundryRequest);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<List<LaundryDto>> updateStatusLaundry(Long id, @RequestBody StatusLaundry statusLaundry){
        try {
            List<LaundryDto> laundryDtoList = laundryService.updateStatusLaundry(id, statusLaundry);
            return ResponseEntity.ok(laundryDtoList);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
