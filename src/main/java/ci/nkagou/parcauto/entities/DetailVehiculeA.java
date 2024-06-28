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
@Table(name = "detail_vehicule")
public class DetailVehiculeA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailVehicule;

    @ManyToOne
    @JoinColumn(name = "employe_dmd")
    private EmployeDmd employeDmd;

    @ManyToOne
    @JoinColumn(name = "id_attribution")
    private Attribution attribution;

}
