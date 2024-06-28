package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Article;
import ci.nkagou.parcauto.entities.Entretien;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailVidangeDtoOut {

    private Long id;

    private int Montant;

    private String article;

    private Entretien entretien;

    private Vehicule vehicule;

}
