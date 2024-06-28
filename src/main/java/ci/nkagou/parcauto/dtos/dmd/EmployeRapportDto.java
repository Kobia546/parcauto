package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.enums.RapportStatut;
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
public class EmployeRapportDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fin;

    @Enumerated(EnumType.ORDINAL)
    private RapportStatut rapportStatut;
}
