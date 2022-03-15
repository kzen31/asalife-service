package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.models.requests.AduanCatering;
import com.asaproject.asalife.domains.models.requests.StatusCatering;
import com.asaproject.asalife.services.CateringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catering")
public class CateringController {
    private final CateringService cateringService;

    @PostMapping("/add")
    public ResponseEntity<List<Catering>> addAduanCatering(Principal principal, @Valid @RequestBody AduanCatering aduanCatering) {
        try {
            List<Catering> caterings = cateringService.addAduanCatering(principal, aduanCatering);
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Catering>> myCaterings(Principal principal) {
        try {
            List<Catering> caterings = cateringService.getUserCaterings(principal);
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @GetMapping("/all")
    public ResponseEntity<List<Catering>> getAllCaterings() {
        try {
            List<Catering> caterings = cateringService.getCaterings();
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/all-by-status")
    public ResponseEntity<List<Catering>> getAllCateringsByStatus(StatusCatering statusCatering) {
        try {
            List<Catering> caterings = cateringService.getCateringsByStatus(statusCatering);
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.MEGAUSER })
    @PutMapping("/update-status")
    public ResponseEntity<Catering> updateStatusAduanCatering(@RequestParam Long id,
                                                              @RequestBody StatusCatering statusCatering) {
        try {
            Catering caterings = cateringService.updateStatusCatering(id, statusCatering);
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            if(e.getMessage().equals("NOT_FOUND")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Catering> findCatering(@RequestParam Long id) {
        try {
            Catering catering = cateringService.getCateringById(id);
            return ResponseEntity.ok(catering);
        } catch (Exception e) {
            if (e.getMessage().equals("NOT_FOUND")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
