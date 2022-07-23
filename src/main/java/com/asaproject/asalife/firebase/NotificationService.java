package com.asaproject.asalife.firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final FcmClient fcmClient;

    public void sendNotificationTopic(NotificationData data) throws Exception {
        Map<String, String> detail = new HashMap<>();
        detail.put("text", data.getMessage());

        try {
            this.fcmClient.fcmClientNotificationToTopic(detail, data);
        }
        catch (InterruptedException | ExecutionException e) {
            throw new Exception("Fail to send notif");
        }
    }

    public void sendNotificationToken(NotificationData data, String token) throws Exception {
        Map<String, String> detail = new HashMap<>();
        detail.put("text", data.getMessage());

        try {
            this.fcmClient.fcmClientNotificationToToken(detail, data, token);
        }
        catch (InterruptedException | ExecutionException e) {
            throw new Exception("Fail to send notif");
        }
    }

    public void sendPushNotificationToToken(NotificationTokenData request) throws Exception {
        try {
            this.fcmClient.sendMessageToToken(request);
        } catch (Exception e) {
            throw new Exception("Fail to send notif");
        }
    }
}
