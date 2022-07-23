package com.asaproject.asalife.firebase;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public void fcmClientNotificationToTopic(Map<String, String> detail, NotificationData data)
            throws InterruptedException, ExecutionException {

        AndroidConfig androidConfig = getAndroidConfig(data);

        ApnsConfig apnsConfig = getApnsConfig(data);

        Message message = getMessageToTopic(detail, data, androidConfig, apnsConfig);

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Sent message: " + response);
    }

    public void fcmClientNotificationToToken(Map<String, String> detail, NotificationData data, String token)
            throws InterruptedException, ExecutionException {

        AndroidConfig androidConfig = getAndroidConfig(data);

        ApnsConfig apnsConfig = getApnsConfig(data);

        Message message = getMessageToToken(detail, data, androidConfig, apnsConfig, token);

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Sent message: " + response);
    }

    private AndroidConfig getAndroidConfig(NotificationData data) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(data.getTopic())
                .setPriority(Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(data.getTopic()).build()).build();
    }

    private ApnsConfig getApnsConfig(NotificationData data) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(data.getTopic()).setThreadId(data.getTopic()).build()).build();
    }

    private Message getMessageToTopic(Map<String, String> detail,
                                      NotificationData data,
                                      AndroidConfig androidConfig,
                                      ApnsConfig apnsConfig) {
        return Message.builder().putAllData(detail).setTopic(data.getTopic())
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setNotification(Notification.builder().setTitle(data.getTitle())
                        .setBody(data.getBody()).build())
                .build();
    }

    private Message getMessageToToken(Map<String, String> detail,
                                      NotificationData data,
                                      AndroidConfig androidConfig,
                                      ApnsConfig apnsConfig,
                                      String token) {
        return Message.builder().putAllData(detail).setTopic(data.getTopic())
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setNotification(Notification.builder().setTitle(data.getTitle())
                        .setBody(data.getBody()).build()).setToken(token)
                .build();
    }

    public void sendMessageToToken(NotificationTokenData request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        System.out.println("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    private Message getPreconfiguredMessageToToken(NotificationTokenData request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationTokenData request) {
        AndroidConfig androidConfig = getAndroidConfigToken(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfigToken(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        Notification.builder().setTitle(request.getTitle())
                                .setBody(request.getBody()).build());
    }

    private AndroidConfig getAndroidConfigToken(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfigToken(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
}
