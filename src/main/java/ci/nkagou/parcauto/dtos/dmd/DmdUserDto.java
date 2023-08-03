package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Statut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DmdUserDto extends @Valid DmdParcDto {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;

    private LocalTime heurePrevue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAttribution;

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
