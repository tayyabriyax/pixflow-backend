package com.practice.pixflow.service;

import com.practice.pixflow.entity.NotificationEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    @Autowired
    public NotificationRepository notificationRepository;

    public void createNotification(UserEntity recipient, String type, String message){
        NotificationEntity notification = new NotificationEntity();
        notification.setRecipientId(recipient);
        notification.setType(type);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
