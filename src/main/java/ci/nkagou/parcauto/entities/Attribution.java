package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "attribution_vehicule")
/*@Table(name = "visites_techniques", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "VIGNETTE_UK", columnNames = "numVignette") })*/
public abstract class Attribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAttribution;

    /*@Column(name = "new_row", nullable = false)
    @ColumnDefault("false")
    private boolean newRow;*/

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAttribution;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateDeDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateArrivee;

    @Enumerated(EnumType.ORDINAL)
    private StatutAttrib statutAttrib;

    @Column(name = "km_debut")
    private Integer kilometrageDebut;
    @Column(name="km_fin")
    private Integer kilometrageFin;

    @Enumerated(EnumType.ORDINAL)
    private TypeAttribution typeAttribution;

    @OneToMany(mappedBy = "attribution")
    private List<DetailVehiculeA> detailVehiculeA;

    @OneToMany(mappedBy = "attribution")
    private List<DetailVehiculeChauffeurA> detailVehiculeChauffeurA;

    @OneToMany(mappedBy = "attribution")
    private List<DetailCarburantA> detailCarburantA;
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehiculeId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private AppUser user;


    @ManyToOne
    @JoinColumn(name = "employe_dmd")
    private EmployeDmd employeDmd;


     public String calculateDuration() {
        if (dateDeDepart != null && dateArrivee != null) {
            Duration duration = Duration.between(dateDeDepart, dateArrivee);
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;

            // Create the formatted string
            return String.format("%dJ-%dH-%dM", days, hours, minutes);
        } else {
            // Handle the case where one or both LocalDateTime instances are null.
            // You can throw an exception or return a default value as needed.
            return null; // Or throw an exception like IllegalArgumentException
        }
    }



}
