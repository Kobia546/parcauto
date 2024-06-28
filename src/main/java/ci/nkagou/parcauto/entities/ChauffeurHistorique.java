package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.enums.StatutHistorique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chauffeur_historique")

public class ChauffeurHistorique  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private StatutHistorique statutHistorique;

    private LocalDateTime dateParcours;

    @ManyToOne
    @JoinColumn(name = "attribution")
    private VehiculeChauffeurAtt vehiculeChauffeurAtt;




}
