package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.MoyenDemande;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dmds")
public class Dmd {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDmd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;

    private LocalTime heurePrevue;

    @Enumerated(EnumType.ORDINAL)
    private MoyenDemande moyenDemande;

    private LocalDateTime dateOperation;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="idVehicule")
    private Vehicule vehicule;*/


    /*  @OneToMany(mappedBy = "employe", fetch = FetchType.EAGER)
    private List<EmployeDmd> employeDmdList;


   @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employe_dmd",
            joinColumns = @JoinColumn(name = "idDmd"),
            inverseJoinColumns = @JoinColumn(name = "idEmploye")
    )
    private List<Employe> employes = new ArrayList<>();*/

}
