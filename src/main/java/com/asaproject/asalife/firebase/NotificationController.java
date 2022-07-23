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
            notificationService.sendNotificationTopic(data);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/custom-notif")
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationData data) {
        try {
            notificationService.sendNotificationTopic(data);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/dummy-token")
    public ResponseEntity<String> sendDummyNotificationToken(@RequestBody String token) {
        NotificationData data = new NotificationData("Sample topic", "Sample notif", "Sample body", "Sample message");

        try {
            notificationService.sendNotificationToken(data, token);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/custom-token/{token}")
    public ResponseEntity<String> sendNotificationToken(@PathVariable String token, @Valid @RequestBody NotificationData data) {
        try {
            notificationService.sendNotificationToken(data, token);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody NotificationTokenData request) {
        try {
            notificationService.sendPushNotificationToToken(request);
            return ResponseEntity.ok("sukses kirim notif");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
