package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Entretien;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailHerbdomadaireDtoOut {


    private Long id;
    //private String dateEntretien;
    /*private String estLavage;

    private String estCarburant;*/

    private boolean estLavage;

    private boolean estCarburant;

    private double litre;

    private Entretien entretien;

    private String vehicule;
}
