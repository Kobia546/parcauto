package ci.nkagou.parcauto.dtos.etat;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DmdPapierResponse {
    private String dateHeureDepart;
    private String moyenDemande;
    private String dateHeureArrivee;
    private String immatriculation;
    private String kmDepart;
    private String kmArrivee;
    private  List <DmdPapierDetailResponse> dmdPapierResponseList ;
    private String nomChauffeur;

}
