package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.TypeAttribution;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EtatAttributionEmployeDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fin;

    private TypeAttribution typeAttribution;

    private Long employe;

    private String nom;
}
