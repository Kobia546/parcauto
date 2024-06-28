package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.SelectChauffeur;
import ci.nkagou.parcauto.enums.SelectVehicule;
import ci.nkagou.parcauto.enums.SelectionEmploye;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EtatAttributionDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fin;

    private Long vehicule;
    private String immatriculation;
    private Long employe;
    private String nom;

}
