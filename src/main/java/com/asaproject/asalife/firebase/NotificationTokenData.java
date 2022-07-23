package com.asaproject.asalife.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTokenData {
    private String token;
    private String topic;
    private String title;
    private String body;
    private String message;
}
