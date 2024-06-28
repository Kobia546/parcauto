package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detail_vidange")
public class DetailVidange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailVidange;

    private int montant;

    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idEntretien")
    private Entretien entretien;

}
