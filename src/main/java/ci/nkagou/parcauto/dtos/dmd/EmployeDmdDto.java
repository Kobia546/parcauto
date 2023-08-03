package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Employe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeDmdDto {

    //private Long id;

    private String nom;
    private String destination;
    private String motifDmd;
    private Employe employe;




}
