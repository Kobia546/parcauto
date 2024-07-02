package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.notification.NotificationRequest;
import ci.nkagou.parcauto.entities.Notification;
import ci.nkagou.parcauto.repositories.NotificationRepository;
import ci.nkagou.parcauto.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailNotificationService {

    private final JavaMailSender javaMailSender;

    private final NotificationRepository notificationRepository;
    private final ConfigProperties configProperties;

    private final NotificationService notificationService;

    private final Environment env;

    @Autowired
    public EmailNotificationService(
            JavaMailSender javaMailSender,
            NotificationRepository notificationRepository,
            ConfigProperties configProperties, NotificationService notificationService, Environment env) {
        this.javaMailSender = javaMailSender;
        this.notificationRepository = notificationRepository;
        this.configProperties = configProperties;
        this.notificationService = notificationService;
        this.env = env;
    }

    @Async
    public void sendHtmlEmail(String subject, String message, /*String from,*/ List<String> toList) throws InterruptedException {
        System.out.println("Sleeping now...");
        // Delai de 5 seconde avant execution du code ci-dessous
        Thread.sleep(5000);

        System.out.println(" HTML email en cours d'envoi ...");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        String to = String.join(", ", toList);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setTo(to);
            //helper.setTo(toList.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(message, true); // Set the second argument to true for HTML content

            javaMailSender.send(mimeMessage);
            System.out.println("HTML Mail envoyé avec succès à : " + to /*Arrays.toString(toList.toArray(new String[0]))*/ + " depuis "  + env.getProperty("spring.mail.username"));

            //for (String to : toList) {
                NotificationRequest request = new NotificationRequest();
                request.setDateTime(LocalDateTime.now());
                request.setA(to);
                request.setSujet(subject);
                request.setMessage(message);
                //notification.setUser(user);
                notificationService.create(request);
                System.out.println("Notification ajoutée avec succès pour : " + to);
            //}
            //System.out.println("Notification ajoutée avec succès pour : " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'email HTML.");
        }
    }

   /* public void addNotification(Notification notification) {
        notificationRepository.save(notification);
    }*/
}
