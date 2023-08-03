package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "marques", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "MARQUE_UK", columnNames = "name") })
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarque;

    @Column(unique=true)
    private String name;

    public Marque(String name) {
        this.name = name;
    }
}
