package ci.nkagou.parcauto.dtos.fonction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class FonctionRequest {
    private Long id;

    private String libelle;
}
