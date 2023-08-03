package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculeHistorique;
import ci.nkagou.parcauto.enums.Motif;
import ci.nkagou.parcauto.enums.StatutVehiculeA;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor

public class VehiculeAttDtoOut {

    private String statutVehiculeA;
    private String motif;
    private String observation;
    private String vehiculeHistorique;
    private Vehicule vehicule;

}
