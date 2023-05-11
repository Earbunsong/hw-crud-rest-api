package com.example.mbanking.api.notification.web;

import com.example.mbanking.api.notification.NotificationService;
import com.example.mbanking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;


    @PostMapping
    public BaseRest<?> pushNotification(@RequestBody CreateNotificationDto notificationDto){
        notificationService.pushNotification(notificationDto);
        return null;
    }
}
