package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.notification.NotificationRequest;
import ci.nkagou.parcauto.entities.Notification;

public interface NotificationService {

    void create(NotificationRequest request);
    Notification ENTITY(NotificationRequest request);
}
