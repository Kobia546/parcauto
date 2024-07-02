package ci.nkagou.parcauto.dtos.dashboard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashbordUserResponseDto {
    private Integer nbreDmdValide;
    private Integer nbreDmdRefuse;
    private Integer nbreDmdTotal;
    private Integer nbreDmdAnnule;
}
