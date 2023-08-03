package ci.nkagou.parcauto.dtos.chauffeur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeDtoOut {

    private Long id;
    private String numMatEmpl;
    private String nom;
    private String prenom;
    private String genre;
    private String fonction;
    private String dateEmbauche;
    private String email;
    private String telephoneEmploye;
    private String dateNaissance;
    private String estSuperieureHierachique;
    private String estChauffeur;
    private String statutChauffeur;
    private String estUtilisateur;
    private String site;
    private String direction;
    private String toNomComplet(){
        return this.prenom + " " + this.nom;
    }

}
