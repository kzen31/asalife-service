package com.asaproject.asalife.firebase;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FcmClient {
    public FcmClient() {
        try {
            ClassPathResource serviceAccount =
                    new ClassPathResource("path/learn-b4272-firebase-adminsdk-bxfz1-08f6fc0946.json"); // it is in resources folder

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setDatabaseUrl("database-path-provided-by-firebase.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fcmClientNotification(Map<String, String> detail, NotificationData data)
            throws InterruptedException, ExecutionException {

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(data.getTopic())
                .setPriority(Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(data.getTopic()).build()).build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(data.getTopic()).setThreadId(data.getTopic()).build()).build();

        Message message = Message.builder().putAllData(detail).setTopic(data.getTopic())
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setNotification(Notification.builder().setTitle(data.getTitle())
                        .setBody(data.getBody()).build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Sent message: " + response);
    }
}
