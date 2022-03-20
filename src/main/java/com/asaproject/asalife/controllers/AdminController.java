package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Secured({ ERole.Constants.ADMIN })
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public ResponseEntity<String> getVerifyingUser() {
        return ResponseEntity.ok("Page Admin");
    }
}
