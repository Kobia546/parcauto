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
@Table(name = "detail_chauffeur")
public class DetailVehiculeChauffeurAttribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailChauffeur;

    @ManyToOne
    @JoinColumn(name = "employe_dmd")
    private EmployeDmd employeDmd;

    @ManyToOne
    @JoinColumn(name = "idAttribution")
    private Attribution attribution;

}
