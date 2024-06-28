package ci.nkagou.parcauto.dtos.dmd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CombineAttributionDtoOut {
    List<AttributionDtoOut> combine;

    public CombineAttributionDtoOut(List<AttributionDtoOut> combine) {
        this.combine = combine;
    }

    public List<AttributionDtoOut> getCombine() {
        return combine;
    }


}
