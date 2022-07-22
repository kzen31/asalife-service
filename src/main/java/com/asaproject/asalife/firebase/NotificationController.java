package com.asaproject.asalife.firebase;

import com.asaproject.asalife.controllers.HandlerController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class NotificationController extends HandlerController {
    private final NotificationService notificationService;

    @PostMapping("/dummy-notif/{topic}")
    public ResponseEntity<String> sendDummyNotification(@PathVariable String topic) {
        NotificationData data = new NotificationData(topic, "Sample notif", "Sample body", "Sample message");
        try {
            notificationService.sendNotification(data);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/custom-notif")
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationData data) {
        try {
            notificationService.sendNotification(data);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
