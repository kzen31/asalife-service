package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController extends HandlerController {

    @Secured({ ERole.Constants.ADMIN })
    @GetMapping("/home")
    public ResponseEntity<String> getHomeAdmin() {
        return ResponseEntity.ok("Page Admin");
    }
}
