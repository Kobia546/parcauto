package ci.nkagou.parcauto.dtos.dashboard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashbordUserResponseDto {
    private Long nbreDmdValide;
    private Long nbreDmdRefuse;
    private Long nbreDmdTotal;
    private Long nbreDmdAnnule;
}
