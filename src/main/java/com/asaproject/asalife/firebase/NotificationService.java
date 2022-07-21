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

    public void sendNotification(NotificationData data) throws Exception {
        Map<String, String> detail = new HashMap<>();
        detail.put("text", data.getMessage());

        try {
            this.fcmClient.fcmClientNotification(detail, data);
        }
        catch (InterruptedException | ExecutionException e) {
            throw new Exception("Fail to send notif");
        }
    }

}
