package ci.nkagou.parcauto.dtos.entretien;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EntretienherDto {
    private LocalDate date;
}
