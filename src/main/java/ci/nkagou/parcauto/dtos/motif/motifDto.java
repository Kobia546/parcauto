package ci.nkagou.parcauto.dtos.motif;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class motifDto {

    private Long id;

    private String nomMotif;

}
