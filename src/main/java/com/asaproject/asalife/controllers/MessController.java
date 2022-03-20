package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.requests.MessRequest;
import com.asaproject.asalife.services.MessService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mess")
public class MessController extends HandlerController {
    private final MessService messService;

    @GetMapping("/all")
    public ResponseEntity<List<Mess>> getAllMess() {
        return ResponseEntity.ok(messService.getAllMess());
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> getAvailability(String name) {
        Boolean result = messService.isMessAvailable(name);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<List<Mess>> addMessName(@RequestBody MessRequest messRequest) {
        try {
            messService.addMess(messRequest);
            return getAllMess();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<List<Mess>> deleteMess(@RequestBody MessRequest messRequest) {
        try {
            messService.deleteMess(messRequest);
            return getAllMess();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
