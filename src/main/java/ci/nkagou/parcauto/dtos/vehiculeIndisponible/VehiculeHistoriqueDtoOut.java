package ci.nkagou.parcauto.dtos.vehiculeIndisponible;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.enums.StatutHistorique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehiculeHistoriqueDtoOut {

    private Long id;
    private String statutHistorique;
    private String dateParcours;
    private VehiculeAtt vehiculeAtt;
}
