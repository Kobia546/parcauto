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
@Table(name = "detail_chauffeur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employe_dmd", "id_attribution"})
        })
public class DetailVehiculeChauffeurA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailChauffeur;

    @ManyToOne

    @JoinColumn(name = "employe_dmd")
    private EmployeDmd employeDmd;

    @ManyToOne

    @JoinColumn(name = "id_attribution")
    private Attribution attribution;

}
