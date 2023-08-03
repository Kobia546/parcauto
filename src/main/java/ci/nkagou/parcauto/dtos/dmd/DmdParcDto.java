package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.UserRole;
import ci.nkagou.parcauto.entities.Vehicule;
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
public class DmdParcDto {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePrevue;
    private LocalTime heurePrevue;
    private String moyenDemande;
    private LocalDateTime dateOperation;


    //private Long idResponsable;
    private Employe employe;
    private Vehicule vehicule;
    private List<EmployeDmdDto> employeDmdDto;


}
