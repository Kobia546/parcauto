package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.EmployeDmd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class DetailADateDtoOut {

    private Long id;

    private String nom;

    private String dateAttribution;

    private String dateDeDepart;
    private String dateArrivee;
    private String statutAttrib;
    private String typeAttribution;
    private String statutVehiculeA;
    private String statutChauffeurA;
    private String motif;
    private String motifChauffeur;
    private String observation;
    private String observationChauffeur;
    private String vehicule;
    private String chauffeur;
    private String employe;
    //private String vehiculeHistorique;
    //private String chauffeurHistorique;
    private String duration;
//    @Column(name="recuCarburant")
//    private String recuCarburant;
    private String immatriculationVehicule;
    private int montant;
    private int litre;



}
