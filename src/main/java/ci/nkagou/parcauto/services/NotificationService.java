package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Notification;
import ci.nkagou.parcauto.repositories.NotificationRepository;
import ci.nkagou.parcauto.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;
    private final ConfigProperties configProperties;

    @Autowired
    public NotificationService(
            JavaMailSender javaMailSender,
            NotificationRepository notificationRepository,
            ConfigProperties configProperties) {
        this.javaMailSender = javaMailSender;
        this.notificationRepository = notificationRepository;
        this.configProperties = configProperties;
    }

    @Async
    public void sendHtmlEmail(String subject, String message, String from, List<String> toList) throws InterruptedException {
        System.out.println("Sleeping now...");
        // Delai de 5 seconde avant execution du code ci-dessous
        Thread.sleep(5000);

        System.out.println("Sending HTML email...");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(toList.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(message, true); // Set the second argument to true for HTML content

            javaMailSender.send(mimeMessage);
            System.out.println("HTML Mail envoyé avec succès");

            for (String to : toList) {
                Notification notification = new Notification();
                notification.setDateTime(LocalDateTime.now());
                notification.setA(to);
                notification.setSujet(subject);
                notification.setMessage(message);
                //notification.setUser(user);
                this.addNotification(notification);
                System.out.println("Notification ajoutée avec succès pour : " + to);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'email HTML.");
        }
    }

    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
