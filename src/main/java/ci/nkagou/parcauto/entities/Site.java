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
@Table(name = "sites", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "SITE_UK", columnNames = "libelle")})
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSite;

    @Column(unique = true)
    private String  libelle;

    public Site(String libelle) {
        this.libelle = libelle;
    }
}
