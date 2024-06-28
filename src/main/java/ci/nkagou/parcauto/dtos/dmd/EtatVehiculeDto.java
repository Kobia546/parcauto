package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.enums.Selection;
import ci.nkagou.parcauto.enums.SelectionEmploye;
import ci.nkagou.parcauto.enums.StatutAttrib;
import ci.nkagou.parcauto.enums.TypeAttribution;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EtatVehiculeDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fin;

    private SelectionEmploye selectionEmploye;

    private Vehicule vehicule;

    private Employe employe;

    //private VehiculeAtt vehiculeAtt;

}
