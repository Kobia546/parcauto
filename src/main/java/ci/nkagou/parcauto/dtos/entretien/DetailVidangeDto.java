package ci.nkagou.parcauto.dtos.entretien;

import ci.nkagou.parcauto.entities.Article;
import ci.nkagou.parcauto.entities.Entretien;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
public class DetailVidangeDto {

    private Long id;

    private int montant;

    private Article article;

    private Entretien entretien;



}
