package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
