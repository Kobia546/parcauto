package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Entretien;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailHerbdomadaireDto {

    private Long id;

    private boolean estLavage;

    private boolean estCarburant;

    private double litre;

    private Entretien entretien;

    private Vehicule vehicule;

}
