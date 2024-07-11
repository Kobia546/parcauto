package ci.nkagou.parcauto.dtos.employe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeResponse {

    private Long id;
    private String nom;
    private String prenoms;
    private String matricule;
    private String email;
    private String genre;
    private String site;
    private String direction;
    private String estChauffeur;
    private String estResponsable;
    private String fonction;

    public static String stringEstChauffeur(Boolean estChauffeur){

        if (estChauffeur){
            return "OUI";
        }
        return "NON";
    }

    public static String stringEstResponsable(Boolean estResponsable){

        if (estResponsable){
            return "OUI";
        }
        return "NON";
    }
}
