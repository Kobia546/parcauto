package ci.nkagou.parcauto.services;



import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutAttrib;

import java.time.LocalDate;
import java.util.List;

public interface AttributionService {

    Attribution findAttributionByid(Long id);
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
    Attribution createCarburant(AttributionCarburantAttDto dto);
    AttributionDtoOut attributionDto(Attribution attribution);
    List<AttributionDtoOut> listAttributionToDto(List<Attribution> attributions);
    /*CombineAttributionDtoOut listAttributionToCombineDto(List<Attribution> attributions);*/
   List<AttributionDtoOut> listAttributionToDtoCarburant(List<CarburantAtt> carburantAtts);
    List<AttributionDtoOut> listAttributionToDtoVehicule(List<VehiculeAtt> vehiculeAtts);
    List<AttributionDtoOut> listAttributionToDtoVehiculeChauffeur(List<VehiculeAtt> vehiculeAtts,List<VehiculeChauffeurAtt> vehiculeChauffeurAtts);
    Attribution updateCommencerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution updateCommencerLaCourseVehicule(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseVehicule(AttributionDtoZ attributionDtoZ);
    Attribution updateTerminerLaCourseCarburant(AttributionDtoZ attributionDtoZ);
    Attribution annulerAttributionVehicule(AttributionDtoZ attributionDtoZ);



    AttributionDtoOut getAttributionDto(Long id, Class<? extends Attribution>... attributionClasses);

    Attribution annulerAttributionVehiculeChauffeur(AttributionDtoZ attributionDtoZ);
    Attribution annulerAttributionCarburant(AttributionDtoZ attributionDtoZ);
    //List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe);
    List<VehiculeAtt> listAttributionByDateBetween(EtatChauffeurDto dto);
    List<Attribution> listAttributionByDateBetween(EtatAttributionDto dto);
    List<Attribution> listAttributionByDateBetweens(EtatAttributionEmployeDto dto);
    List<Attribution> listAttributionByDateBetweenAndVehicule(EtatAttributionDto dto);
    List<Attribution> listAttributionByDateBetweenAndEmploye(EtatAttributionDto dto);
    //List<Attribution> listAttributionByDateBetweenAndEmployes(EtatAttributionEmployeDto dto);
    List<Attribution> listAttributionByDateBetweenAndEmployeAndVehicule(EtatAttributionDto dto);
    List<Attribution> listEtatAttribution(EtatAttributionDto dto);
    //List<Attribution> listEtatAttributionEmploye(EtatAttributionEmployeDto dto);

}
