package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.requests.LaundryRequest;
import com.asaproject.asalife.domains.models.requests.StatusLaundry;
import com.asaproject.asalife.domains.models.responses.ApiResponse;
import com.asaproject.asalife.domains.models.responses.LaundryDto;
import com.asaproject.asalife.services.LaundryService;
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
@RequestMapping("/api/laundry")
public class LaundryController extends HandlerController {
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
    public ResponseEntity<ApiResponse> addLaundryByUser(Principal principal, @Valid @RequestBody LaundryRequest laundryRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());
        try {
            laundryService.addLaundry(principal, laundryRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Aduan Laundry").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStatusLaundry(@PathVariable Long id, @Valid @RequestBody StatusLaundry statusLaundry){
        try {
            laundryService.updateStatusLaundry(id, statusLaundry);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Status Laundry").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping ("/delete-record/{id}")
    public ResponseEntity<ApiResponse> deleteRecordLaundry(@PathVariable Long id) {
        try {
            laundryService.deleteLaundry(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Record Laundry").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record Laundry Not Found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not valid", e.getCause());
        }
    }
}
