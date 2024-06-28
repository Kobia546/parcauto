package ci.nkagou.parcauto.dtos.etat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DmdPapierDetailResponse {
    private String  nomPrenom;
    private String destination;
    private String motif;
    //private String parcours;

}
