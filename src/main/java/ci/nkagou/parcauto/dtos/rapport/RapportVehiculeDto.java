package ci.nkagou.parcauto.dtos.rapport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RapportVehiculeDto {
    private Long id;
    private String immatriculation;
}
