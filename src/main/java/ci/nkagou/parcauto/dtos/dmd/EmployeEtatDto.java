package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.enums.RapportStatut;
import ci.nkagou.parcauto.enums.Selection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeEtatDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fin;

    @Enumerated(EnumType.ORDINAL)
    private Selection selection;

    private Employe employe;

}
