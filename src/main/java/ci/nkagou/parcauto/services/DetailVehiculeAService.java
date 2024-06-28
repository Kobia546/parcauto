package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.DetailVehiculeA;
import ci.nkagou.parcauto.entities.DetailVehiculeChauffeurA;
import ci.nkagou.parcauto.entities.Employe;

import java.util.List;

public interface DetailVehiculeAService {

    DetailVehiculeA findById(Long id);
    //List<DetailVehiculeA> listDetailVehiculeAByDateBetweenAndEmployeDmdEmploye(EtatAttributionEmployeDto dto);
    DetailADateDtoOut DetailAttributionDto(DetailVehiculeA detailVehiculeA);
    List<DetailADateDtoOut> listDetailAttributionDto(List<DetailVehiculeA> detailVehiculeA);
    //List<DetailADateDto> listDetailAttributionByDateAttributionBetweenEmployeDmdEmploye(EtatAttributionEmployeDto dto);
    List<DetailVehiculeA> getListDetailVehiculeAByAttribution(Attribution attribution);
    List<DetailVehiculeA> getListDetailVehiculeAByListAttribution(List<Attribution> attribution);
    List<DetailVehiculeA> getListDetailVehiculeAByListAttributionEmploye(List<Attribution> attribution, Employe employe);
    List<DetailVehiculeA> listDetailVehiculeAByDateBetween(EtatAttributionEmployeDto dto);

}
