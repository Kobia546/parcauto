package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vehicules", uniqueConstraints = { @UniqueConstraint(name = "VEHICULE_UK", columnNames = "immatriculation") })
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(unique = true)
    private String immatriculation;

    @Enumerated(EnumType.ORDINAL)
    private Couleur couleur;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAchat;

    private String carteGrise;

    private String numeroChassis;

    @Enumerated(EnumType.ORDINAL)
    private StatutVehicule statutVehicule;

    @ManyToOne
    @JoinColumn(name = "idMarque")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "idTypeVehicule")
    private Typevehicule typevehicule;

    @Size(max = 600)
    private String raison;

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
    private List<EntretienVidange> entretienVidange;
}
