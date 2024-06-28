package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
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

    @FutureOrPresent(message = "doit être une date dans le présent ou le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;

    private LocalTime heurePrevue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;

    private int montant;

    private String moyenDemande;

    private Motif motif;

    private Destination destination;

    private  Integer kilometrageDebut;

    private Statut statut;

    private String observation;

    private Employe employe;

    private Long responsable;

    private Long chauffeur;

    private Vehicule vehicule;

}
