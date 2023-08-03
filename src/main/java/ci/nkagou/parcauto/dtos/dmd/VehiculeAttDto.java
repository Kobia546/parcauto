package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculeHistorique;
import ci.nkagou.parcauto.enums.Motif;
import ci.nkagou.parcauto.enums.StatutVehiculeA;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class VehiculeAttDto {

    private StatutVehiculeA statutVehiculeA;
    private Motif motif;
    private String observation;
    private List<VehiculeHistorique> vehiculeHistorique;
    private String vehicule;

}
