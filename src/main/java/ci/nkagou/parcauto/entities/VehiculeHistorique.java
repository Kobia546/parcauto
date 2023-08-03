package ci.nkagou.parcauto.entities;

import ci.nkagou.parcauto.enums.StatutHistorique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicule_historique")

public class VehiculeHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private StatutHistorique statutHistorique;

    private LocalDateTime dateParcours;

    @ManyToOne
    @JoinColumn(name = "attribution")
    private VehiculeAtt vehiculeAtt;



}
