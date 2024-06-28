package ci.nkagou.parcauto.dtos.dmd;

import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.enums.StatutAttrib;
import ci.nkagou.parcauto.enums.TypeAttribution;
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

public class AttributionCarburantAttDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDeDepart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAttribution;
    private StatutAttrib statutAttrib;
    @Enumerated(EnumType.ORDINAL)
    private MoyenDemande moyenDemande;
    private TypeAttribution typeAttribution;
    private int montant;
    //private List<Long> dmdUserDto;
    private List<DetailCarburantADto> detailCarburantADto;

}
