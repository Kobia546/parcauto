package ci.nkagou.parcauto.dtos.vehicule;

import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VehiculeDtoZ {

    private Long idVehicule;
    private String immatriculation;
    private Couleur couleur;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAchat;
    private String carteGrise;
    private String numeroChassis;
    private StatutVehicule statutVehicule;
    private Marque marque;
    private Typevehicule typeVehicule;
    private String raison;

}
