package com.example.mbanking.api.notification;

import com.example.mbanking.api.notification.web.CreateNotificationDto;
import com.example.mbanking.api.notification.web.NotificationDto;

public interface NotificationService {

    boolean pushNotification(CreateNotificationDto notificationDto);
}
