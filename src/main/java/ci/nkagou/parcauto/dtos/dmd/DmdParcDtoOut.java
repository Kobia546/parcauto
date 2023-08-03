package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DmdParcDtoOut {

    private Long id;

    private String datePrevue;
    private String heurePrevue;
    private String moyenDemande;
    private String dateOperation;
    private String employe;
    //private List<EmployeDmdDto> employeDmdDto;

}
