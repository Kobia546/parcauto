package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.enums.Motifs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.FutureOrPresent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class AttributionDtoZ  {

    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAttribution;

    @FutureOrPresent(message = "doit être une date dans le présent ou le futur")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;

    @FutureOrPresent(message = "doit être une date dans le présent ou le futur")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateArrivee;

    private Integer kilometrageFin;

    private StatutAttrib statutAttrib;
    private Integer kilometrageDebut;
    private TypeAttribution typeAttribution;
    private StatutVehiculeA statutVehiculeA;
    private StatutChauffeurA statutChauffeurA;
    private Motifs motif;
    private MotifChauffeur motifChauffeur;
    private String observation;
    private String observationChauffeur;
    private Employe employe;
    public EmployeDmd employeDmd;
    public Vehicule vehicule;
    private List<VehiculeHistorique> vehiculeHistorique;
    private List<ChauffeurHistorique> chauffeurHistorique;
    private List<DetailVehiculeA> detailVehiculeA;
    private List<DetailVehiculeChauffeurA> detailVehiculeChauffeurA;
    private List<DetailCarburantA> detailCarburantA;
//    @Column(name="recuCarburant")
//    private MultipartFile recuCarburant;
    private String immatriculationVehicule;
    public Duration calculateDuration() {
        if (dateDeDepart != null && dateArrivee != null) {
            return Duration.between(dateDeDepart, dateArrivee);
        } else {
            return null;
        }
    }
    private int montant;
    private int litre;
    /*@AssertTrue(message = "La date de départ doit être antérieure à la date d'arrivée")
    private boolean isValidDateRange() {
        return dateDeDepart == null || dateArrivee == null || dateDeDepart.isBefore(dateArrivee);
    }*/

}
