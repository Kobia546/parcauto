package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeDmdDtoOut {

    private Long id;
    private String nom;
    private String Destination;
    private String motifDmd;


}
