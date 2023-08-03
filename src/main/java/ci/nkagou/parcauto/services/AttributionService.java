package ci.nkagou.parcauto.services;



import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutAttrib;

import java.util.List;

public interface AttributionService {

    /*List<Attribution> all();
    Attribution getById(Long id);
    void create(AttributionDto dto);
    Attribution updateTerminerLaCourse(AttributionDtoZ attributionDtoZ);
    Attribution updateCommencerLaCourse(AttributionDtoZ AttributionDtoZ);
    void delete(Attribution attribution);
    AttributionDtoOut attributionDto(Attribution attribution);
    List<AttributionDtoOut> listAttributionToDto(List<Attribution> attributions);
    List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe);
    Attribution commencer(Long id,Attribution attribution);
    Attribution annulerAttribution(AttributionDtoZ attributionDtoZ);*/

    List<Attribution> all();
    List<CarburantAtt> allAttribution();
    List<VehiculeAtt> allAttributionVehicule();
    //List<VehiculeAtt> AttributionVehiculeByStatutAttrib(StatutAttrib statutAttrib1,StatutAttrib statutAttrib);
    List<VehiculeChauffeurAtt> allAttributionVehiculeChauffeur();
    //List<VehiculeChauffeurAtt> AttributionVehiculeChauffeurByStatutChauffeur(StatutAttrib statutAttrib,StatutAttrib statutAttrib1);
    VehiculeChauffeurAtt findById(Long id);
    VehiculeAtt findVEById(Long id);
    CarburantAtt findCCById(Long id);
    Attribution getById(Long id);
    void create(AttributionDto dto);
    void createVehiculeChauffeur(AttributionVehiculeChauffeurAttDto dto);
    void createCarburant(AttributionCarburantAttDto dto);
    AttributionDtoOut attributionDto(Attribution attribution);
    List<AttributionDtoOut> listAttributionToDto(List<Attribution> attributions);
    List<AttributionDtoOut> listAttributionToDtoCarburant(List<CarburantAtt> carburantAtts);
    List<AttributionDtoOut> listAttributionToDtoVehicule(List<VehiculeAtt> vehiculeAtts);
    List<AttributionDtoOut> listAttributionToDtoVehiculeChauffeur(List<VehiculeAtt> vehiculeAtts,List<VehiculeChauffeurAtt> vehiculeChauffeurAtts);
    Attribution updateCommencerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution updateCommencerLaCourseVehicule(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseVehicule(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseCarburant(AttributionDtoZ attributionDtoZ);
    Attribution annulerAttributionVehicule(AttributionDtoZ attributionDtoZ);
    Attribution annulerAttributionVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution annulerAttributionCarburant(AttributionDtoZ attributionDtoZ);
    List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe);

}
