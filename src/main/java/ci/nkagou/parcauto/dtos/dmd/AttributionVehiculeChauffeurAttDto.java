package ci.nkagou.parcauto.dtos.dmd;


import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.enums.StatutAttrib;
import ci.nkagou.parcauto.enums.TypeAttribution;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AttributionVehiculeChauffeurAttDto  {

    private Long vehicule;
    private Long employe;
    private Integer kilometrageDebut;
    private Integer kilometrageFin;
   /* @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAttribution;*/
    @Enumerated(EnumType.ORDINAL)
    private MoyenDemande moyenDemande;
    //private StatutAttrib statutAttrib;
    private TypeAttribution typeAttribution;
    //private List<Long> dmdUserDto;
    //private DmdUserDto dmdUserDtos;
    private List<DetailVehiculeChauffeurADto> detailVehiculeChauffeurADto;

}
