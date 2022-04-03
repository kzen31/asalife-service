package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.requests.MessRequest;
import com.asaproject.asalife.domains.models.responses.ApiResponse;
import com.asaproject.asalife.domains.models.responses.MessDto;
import com.asaproject.asalife.services.MessService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mess")
public class MessController extends HandlerController {
    private final MessService messService;

    @GetMapping("/all")
    public ResponseEntity<List<MessDto>> getAllMess() {
        return ResponseEntity.ok(messService.getAllMess());
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> getAvailability(String name) {
        Boolean result = messService.isMessAvailable(name);
        return ResponseEntity.ok(result);
    }

    @Secured({ ERole.Constants.ADMIN })
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMessName(@Valid @RequestBody MessRequest messRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());

        try {
            messService.addMess(messRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Mess Record").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMess(@PathVariable Long id) {
        try {
            messService.deleteMess(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Mess").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
