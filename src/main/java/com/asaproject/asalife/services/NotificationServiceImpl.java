package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Notification;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.repositories.NotificationRepository;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepo;

    @Override
    public List<Notification> getNotifications(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return notificationRepo.findByUser_Id(user.getId());
    }
}
