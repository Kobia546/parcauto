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
@Table(name = "vehicule_periode_assurance")
public class VehiculePeriodeAssurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "idPeriode")
    private Periode periode;

    @ManyToOne
    @JoinColumn(name = "idAssurance")
    private Assurance assurance;


}
