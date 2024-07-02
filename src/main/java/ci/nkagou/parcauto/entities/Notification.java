package ci.nkagou.parcauto.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime DateTime;

    private String a;
    private String sujet;

    /*@Lob*/
    @Column(columnDefinition = "TEXT")
    private String message;

  /*  @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser user;*/

    /*@ManyToOne
    @JoinColumn(name = "userId")
    private Employe employe;*/

}
