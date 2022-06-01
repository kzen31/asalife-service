package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.responses.MyProfile;
import com.asaproject.asalife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController extends HandlerController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/my")
    public ResponseEntity<MyProfile> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getMyProfile(principal));
    }

    @GetMapping("/mt")
    public ResponseEntity<List<User>> getMt() {
        return ResponseEntity.ok(userService.getMt());
    }
}
