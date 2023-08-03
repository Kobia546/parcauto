package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DmdUserDtoH {

    private Long id;
    private String datePrevue;
    private String heurePrevue;
    private String moyenDemande;
    private String motif;
    private String destination;
    private String dateOperation;
    private String statut;
    private String nomComplet;
    private Employe chauffeur;
    private Vehicule vehicule;

}
