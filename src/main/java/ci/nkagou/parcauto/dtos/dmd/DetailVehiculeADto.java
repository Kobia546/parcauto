package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetailVehiculeADto {

    //private Long id;


    private Long idEmployeDmd;

    //private Attribution attribution;
}
