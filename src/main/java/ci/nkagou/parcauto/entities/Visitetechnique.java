package ci.nkagou.parcauto.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visites_techniques", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "VISITE_TECHNIQUE_VIGNETTE_UK", columnNames = "numVignette") })
public class Visitetechnique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisite;

    @Column(unique = true)
    private  String numVignette;

    private LocalDate dateVisitTechn;
    private LocalDate dateProchaineVisitTechn;
    private String observationVisitTechn;
}
