package ci.nkagou.parcauto.dtos.entretien;


import ci.nkagou.parcauto.entities.DetailHerbdomadaire;
import ci.nkagou.parcauto.entities.Vehicule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EntretienHerbdomadaireDto {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEntretien;
    /* @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebutSemanine;
    private LocalDate dateFinSemanine;*/
    //private Long vehicule;
    private List<DetailHerbdomadaireDto> detailHerbdomadaireDto;

}
