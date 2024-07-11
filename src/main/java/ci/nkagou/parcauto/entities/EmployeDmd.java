package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employe_dmd")
public class EmployeDmd{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployeDmd;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="idEmploye", nullable = false)
    private Employe employe;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="idDmd" , nullable = false)
    private Dmd dmd;

    /*private String destination;

    private String motifDmd;*/

    @ManyToOne
    @JoinColumn(name = "idDestination")
    private Destination destination;
//    private String precision;

    @ManyToOne
    @JoinColumn(name = "idMotif")
    private Motif motif;

    @Enumerated(EnumType.ORDINAL)
    private Statut statut;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Size(max = 600)
    private String observation;

   @Column(nullable = true, columnDefinition = "bigint default 0")
    private Long responsable;

}
