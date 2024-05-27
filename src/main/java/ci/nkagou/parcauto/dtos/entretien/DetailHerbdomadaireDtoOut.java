package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Entretien;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EntretienHerbdomadaireDtoOut {


    private Long id;
    private String dateEntretien;
    private String estLavage;

    private String estCarburant;

    private int litre;

    //private Entretien entretien;

    private String vehicule;
}
