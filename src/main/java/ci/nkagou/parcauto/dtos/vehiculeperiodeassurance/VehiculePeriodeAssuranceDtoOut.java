package ci.nkagou.parcauto.dtos.vehiculeperiodeassurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculePeriodeAssuranceDtoOut {

    private Long id;
    private String immatriculation;
    private String marque;
    private int exercice;
    private String numeroPolice;
    private String societe;
    private String debutContrat;
    private String finContrat;

}
