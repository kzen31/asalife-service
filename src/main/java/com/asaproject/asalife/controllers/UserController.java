package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.responses.MyProfile;
import com.asaproject.asalife.services.NotificationService;
import com.asaproject.asalife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController extends HandlerController {
    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

//    @GetMapping("/profile/edit")
//    public ResponseEntity<UpdateUser> getProfileEdit(Principal principal) {
//        return ResponseEntity.ok(userService.getProfileUpdate(principal));
//    }
//
//    @PutMapping("/profile/edit")
//    public ResponseEntity<UpdateUser> updateProfile(Principal principal, @Valid @RequestBody UpdateUser updateUser) {
//        return ResponseEntity.ok(userService.updateUser(principal, updateUser));
//    }

    @GetMapping("/profile")
    public ResponseEntity<MyProfile> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getMyProfile(principal));
    }

//    @GetMapping("/notifications")
//    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(Principal principal) {
//        return ResponseEntity.ok(ApiResponse.<List<Notification>>builder().data(notificationService.getNotifications(principal)).build());
//    }
}
