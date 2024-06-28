package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Destination;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Motif;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Statut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class DmdUserDtoS extends @Valid DmdParcDto {

    private Long id;

    @FutureOrPresent(message = "doit être une date dans le présent ou le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;

    private LocalTime heurePrevue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;

    private int montant;

    private String moyenDemande;

    private String motif;

    private String destination;

    private Statut statut;

    private String observation;



    private Employe employe;

    private Long responsable;

    private Long chauffeur;

    private Vehicule vehicule;


}
