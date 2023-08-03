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

@Table(name = "directions", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "DIRECTION_UK", columnNames = "libelle") })
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDirection;

    private String libelle;

    public Direction(String libelle) {
        this.libelle = libelle;
    }
}
