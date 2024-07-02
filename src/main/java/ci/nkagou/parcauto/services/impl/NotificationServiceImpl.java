package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.notification.NotificationRequest;
import ci.nkagou.parcauto.entities.Notification;
import ci.nkagou.parcauto.repositories.NotificationRepository;
import ci.nkagou.parcauto.services.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(NotificationRequest request) {
        repository.save(this.ENTITY(request));
    }

    @Override
    public Notification ENTITY(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setSujet(request.getSujet());
        notification.setA(request.getA());
        notification.setDateTime(request.getDateTime());
        return notification;
    }
}
