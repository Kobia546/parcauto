package ci.nkagou.parcauto.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    private LocalDateTime DateTime;
    private String a;
    private String sujet;
    @Column(columnDefinition = "TEXT")
    private String message;
}
