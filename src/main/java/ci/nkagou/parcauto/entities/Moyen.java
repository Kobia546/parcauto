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
@Table(name = "moyens", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "MOYEN_UK", columnNames = "libelle") })
public class Moyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMoyen;

    @Column(unique = true)
    private String libelle;
}
