package ci.nkagou.parcauto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("EH")
public class Entretienherdomadaire extends Entretien {

    private LocalDate dateDebutSemanine;
    private LocalDate dateFinSemanine;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;
}
