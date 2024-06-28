package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.VehiculeHistorique;
import ci.nkagou.parcauto.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class VehiculeAttDto {

    private StatutVehiculeA statutVehiculeA;
    private Motifs motif;
    private String observation;
    private List<VehiculeHistorique> vehiculeHistorique;
    private String vehicule;



}
