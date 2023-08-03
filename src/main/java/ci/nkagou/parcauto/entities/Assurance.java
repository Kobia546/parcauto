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

@Table(name = "assurances", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "ASSURANCE_POLICE_UK", columnNames = "numPolice") })
public class Assurance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssurance;

    @Column(unique = true)
    private String  numPolice;
    private String nomSociete;
    private LocalDate dateEtablissement;
    private String adresseSociete;
    private LocalDate dateDebutContrat;
    private LocalDate dateFinContrat;



}
