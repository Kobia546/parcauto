package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.Motif;
import ci.nkagou.parcauto.enums.StatutVehiculeA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("VE")

public class VehiculeAtt extends Attribution {

    @Enumerated(EnumType.ORDINAL)
    private StatutVehiculeA statutVehiculeA;

    @Enumerated(EnumType.ORDINAL)
    private Motif motif;

    @Size(max = 600)
    private String observation;

    @OneToMany(mappedBy = "vehiculeAtt")
    private List<VehiculeHistorique> vehiculeHistorique;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;

}
