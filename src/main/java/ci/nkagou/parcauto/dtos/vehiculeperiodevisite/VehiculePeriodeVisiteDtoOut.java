package ci.nkagou.parcauto.dtos.vehiculeperiodevisite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePeriodeVisiteDtoOut {
    private Long id;
    private String immatriculation;
    private String marque;
    private int exercice;
    private  String numVignette;
    private String dateVisitTechn;
    private String dateProchaineVisitTechn;
}
