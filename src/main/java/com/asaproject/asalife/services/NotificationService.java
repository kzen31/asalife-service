package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Notification;

import java.security.Principal;
import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(Principal principal);
}
