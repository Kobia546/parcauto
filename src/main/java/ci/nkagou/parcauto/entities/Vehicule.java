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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vehicules", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "VEHICULE_UK", columnNames = "immatriculation") })
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(unique = true)
    private String immatriculation;

    //vehicule.setCouleur(Couleur.JAUNE);
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
    @JoinColumn(name ="idTypeVehicule")
    private Typevehicule typevehicule;

    @Size(max = 600)
    private String raison;

    public Vehicule(Long idVehicule,String immatriculation, Couleur couleur, LocalDate dateAchat, String carteGrise, Marque marque, Typevehicule typevehicule, StatutVehicule statutVehicule,String numeroChassis) {
        this.idVehicule = idVehicule;
        this.immatriculation = immatriculation;
        this.couleur = couleur;
        this.dateAchat = dateAchat;
        this.carteGrise = carteGrise;
        this.numeroChassis = numeroChassis;
        this.marque = marque;
        this.typevehicule = typevehicule;
        this.statutVehicule = statutVehicule;
    }

    public Vehicule(String idVehicule) {
        this.idVehicule = Long.parseLong(idVehicule);
    }

    // static factory method
    public static Vehicule fromString(String id) {
        return new Vehicule(id);
    }
}
