package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class AttributionDtoZ  {

    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAttribution;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateArrivee;
    private StatutAttrib statutAttrib;
    private TypeAttribution typeAttribution;
    private StatutVehiculeA statutVehiculeA;
    private StatutChauffeurA statutChauffeurA;
    private Motif motif;
    private MotifChauffeur motifChauffeur;
    private String observation;
    private String observationChauffeur;
    private Employe employe;
    private EmployeDmd employeDmd;
    private Vehicule vehicule;
    private List<VehiculeHistorique> vehiculeHistorique;
    private List<ChauffeurHistorique> chauffeurHistorique;
    @Column(name="recuCarburant")
    private MultipartFile recuCarburant;
    private String immatriculationVehicule;
    private int montant;
    private int litre;

}
