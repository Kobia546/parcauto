package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.DetailCarburantA;
import ci.nkagou.parcauto.entities.DetailVehiculeA;
import ci.nkagou.parcauto.entities.Employe;

import java.util.List;

public interface DetailCarburantAService {

    DetailCarburantA findById(Long id);
    //List<DetailCarburantA> listDetailCarburantAByDateBetweenAndEmployeDmdEmploye(EtatAttributionEmployeDto dto);

    DetailADateDtoOut DetailAttributionDto(DetailCarburantA detailCarburantA);
    List<DetailADateDtoOut> listDetailAttributionDto(List<DetailCarburantA> detailCarburantA);
    //List<DetailADateDto> listDetailAttributionByDateAttributionBetweenEmployeDmdEmploye(EtatAttributionEmployeDto dto);
    List<DetailCarburantA> getListDetailCarburantAByAttribution(Attribution attribution);
    List<DetailCarburantA> getListDetailCarburantAByListAttribution(List<Attribution> attribution);
    List<DetailCarburantA> getListDetailCarburantAByListAttributionEmploye(List<Attribution> attribution, Employe employe);
    List<DetailCarburantA> listDetailCarburantAByDateBetween(EtatAttributionEmployeDto dto);
}
