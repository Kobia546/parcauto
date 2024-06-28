package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DmdUserDtoOut {

    private Long id;
    private String datePrevue;
    private String heurePrevue;
    private String moyenDemande;
    private String motif;
    private String destination;
    private String dateOperation;
    private String statut;
    private String observation;
    private String nomComplet;
    private String chauffeur;
    private String vehicule;

}
