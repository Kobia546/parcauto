package ci.nkagou.parcauto.dtos.vehiculeIndisponible;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.enums.StatutHistorique;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VehiculeHistoriqueDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private StatutHistorique statutHistorique;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateParcours;
    private VehiculeAtt vehiculeAtt;
}
