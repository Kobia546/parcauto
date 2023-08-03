package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
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


    private LocalDateTime dateDeDepart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateArrivee;

    @Enumerated(EnumType.ORDINAL)
    private StatutAttrib statutAttrib;

    @Enumerated(EnumType.ORDINAL)
    private TypeAttribution typeAttribution;

    @ManyToOne
    @JoinColumn(name = "employe_dmd")
    private EmployeDmd employeDmd;

}
