package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.ChauffeurHistorique;
import ci.nkagou.parcauto.entities.VehiculeHistorique;
import ci.nkagou.parcauto.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AttributionDtoOut {

    private Long id;
    /*@Column(name = "new_row", nullable = false, columnDefinition = "boolean default false")
    private boolean newRow;*/
    private String nomComplet;
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
    private String vehiculeHistorique;
    private String chauffeurHistorique;
    private String recuCarburant;
    private String immatriculationVehicule;
    private int montant;
    private int litre;

}
