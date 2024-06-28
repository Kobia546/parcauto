package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.SelectionChauffeur;
import ci.nkagou.parcauto.enums.SelectionEmploye;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EtatChauffeurDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime debut;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fin;

    private SelectionChauffeur selectionChauffeur;

    private Vehicule vehicule;

    private Employe employe;
}
