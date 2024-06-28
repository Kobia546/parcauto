package ci.nkagou.parcauto.dtos.chauffeurhistorique;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.VehiculeChauffeurAtt;
import ci.nkagou.parcauto.enums.StatutHistorique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChauffeurHistoriqueDtoOut {

    private Long id;
    private String statutHistorique;
    private String dateParcours;
    private String employe;
    private String vehicule;
    private StringBuilder nomEmploye;
    private StringBuilder destination;

}
