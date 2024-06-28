package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDto;
import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.DetailVehiculeA;
import ci.nkagou.parcauto.entities.DetailVehiculeChauffeurA;
import ci.nkagou.parcauto.entities.Employe;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailVehiculeChauffeurAService {

    List<DetailVehiculeChauffeurA>  All();
    DetailADateDtoOut DetailAttributionDto(DetailVehiculeChauffeurA detailVehiculeChauffeurA);
    List<DetailADateDtoOut> listDetailAttributionDto(List<DetailVehiculeChauffeurA> detailVehiculeChauffeurA);
    //List<DetailADateDto> listDetailAttributionByDateAttributionBetweenEmployeDmdEmploye(EtatAttributionEmployeDto dto);
    List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByAttribution(Attribution attribution);
    List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByListAttribution(List<Attribution> attribution);
    List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByListAttributionEmploye(List<Attribution> attribution,Employe employe);
    List<DetailVehiculeChauffeurA> listDetailVehiculeChauffeurAByDateBetween(EtatAttributionEmployeDto dto);
}
