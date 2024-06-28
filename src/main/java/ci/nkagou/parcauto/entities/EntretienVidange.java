package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("EV")
public class EntretienVidange extends Entretien {

    private int montantTotal;

    @Column(name="recuEntretien")
    private String recuEntretien;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;

    private int ancienKilometrage;

    private int nouveauKilometrage;

}
