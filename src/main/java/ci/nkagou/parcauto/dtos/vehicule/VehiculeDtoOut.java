package ci.nkagou.parcauto.dtos.vehicule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehiculeDtoOut {

    private Long id;
    private String immatriculation;
    private String couleur;
    private String statutVehicule;
    private String dateAchat;
    private String numeroChassis;
    private String carteGrise;
    private String marque;
    private String typeVehicule;
    private String raison;
    /*private String dateIndisponibilité;*/
    /*private String statutVehiculeIn;*/
}
