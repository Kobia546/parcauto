package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.EmployeDmd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetailADateDto {

    private Long id;

    private LocalDateTime dateAttribution;

    private EmployeDmd employeDmd;

    private Attribution attribution;

}
