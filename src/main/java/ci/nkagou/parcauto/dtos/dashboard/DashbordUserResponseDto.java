package ci.nkagou.parcauto.dtos.dashboard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashbordUserResponseDto {
    private String nbreDmdValide;
    private String nbreDmdRefuse;
    private String nbreDmdTotal;
    private String nbreDmdAnnule;
}
