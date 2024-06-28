package ci.nkagou.parcauto.entities;


import ci.nkagou.parcauto.enums.MotifChauffeur;
import ci.nkagou.parcauto.enums.Motifs;
import ci.nkagou.parcauto.enums.StatutChauffeurA;
import ci.nkagou.parcauto.enums.StatutVehiculeA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("VC")

public class VehiculeChauffeurAtt extends Attribution {

    @Enumerated(EnumType.ORDINAL)
    private StatutVehiculeA statutVehiculeA;

    @Enumerated(EnumType.ORDINAL)
    private StatutChauffeurA statutChauffeurA;

    @Enumerated(EnumType.ORDINAL)
    private Motifs motif;

    @Enumerated(EnumType.ORDINAL)
    private MotifChauffeur motifChauffeur;

    @Size(max = 600)
    private String observation;

    @Size(max = 600)
    private String observationChauffeur;

    @ManyToOne
    @JoinColumn(name = "idEmployé")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "idVehicule")
    private Vehicule vehicule;

    @OneToMany(mappedBy = "vehiculeChauffeurAtt")
    private List<ChauffeurHistorique> chauffeurHistorique;

}
