package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import javax.persistence.UniqueConstraint;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "periodes", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "PERIODE_UK", columnNames = "exercice") })

public class Periode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeriode;

    @Column(unique = true)
    private int exercice;
}
