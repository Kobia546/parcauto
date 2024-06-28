package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.UserRole;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DmdParcDto  {

    private Long id;

    @FutureOrPresent(message = "doit être une date dans le présent ou le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;

    private LocalTime heurePrevue;
    private String moyenDemande;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateOperation;

    //private Long idResponsable;
    private Employe employe;
    private Vehicule vehicule;
    private List<EmployeDmdDto> employeDmdDto;


}
