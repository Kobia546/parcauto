package ci.nkagou.parcauto.dtos.dmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttributionWrapperDto {

    private AttributionDto attributionDto;
    private AttributionVehiculeChauffeurAttDto attributionVehiculeChauffeurAttDto;
}
