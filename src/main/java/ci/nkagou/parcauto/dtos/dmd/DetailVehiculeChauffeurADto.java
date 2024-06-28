package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.entities.VehiculeChauffeurAtt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetailVehiculeChauffeurADto {


    private Long idEmployeDmd;

    //private EmployeDmd employeDmd;

    //private Attribution attribution;

    /*public DetailVehiculeChauffeurADto(Long id,EmployeDmd employeDmd,Attribution attribution) {
        this.id = id;
        this.employeDmd = employeDmd;
        this.attribution = attribution;
    }*/

}
