package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}