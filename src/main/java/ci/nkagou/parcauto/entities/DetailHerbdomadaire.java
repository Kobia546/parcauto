package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detail_herbdomadaire")
public class DetailHerbdomadaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailHerbdomadaire;

    private boolean estLavage;

    private boolean estCarburant;

    private double litre;

    @ManyToOne
    @JoinColumn(name = "idEntretien")
    private Entretien entretien;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;
}
