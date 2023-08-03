package ci.nkagou.parcauto.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "typevehicules", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "TYPE_VEHICULE_UK", columnNames = "libelle") })
public class Typevehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypevehicule;

    @Column(unique=true)
    private String libelle;

    public Typevehicule(String libelle) {
        this.libelle = libelle;
    }
}
