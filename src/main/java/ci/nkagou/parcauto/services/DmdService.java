package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;

import java.util.List;

public interface DmdService {

    List<EmployeDmd> listEmployeDmdByStatutDirection(Statut statut, Direction directionResponsable);
    List<EmployeDmd> listEmployeDmdsByStatut(Statut statut);
    DmdUserDtoOut dmdUserToDto(EmployeDmd employeDmd);
    DmdParcDtoOut dmdParcToDto(EmployeDmd employeDmd);
    List<DmdUserDtoOut> listDmdsToDto(List<EmployeDmd> dmds);
    List<DmdParcDtoOut> listDmdsParcToDto(List<EmployeDmd> dmds);
    /*List<DmdUserDtoOut> getById(List<Long> id);*/
    List<EmployeDmd> all();
    EmployeDmd findById(Long id);
    List<EmployeDmd> findEmployeDmdsByEmploye(Employe employe);
    List<EmployeDmd> findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande moyenDemande,Statut statut);
    /*List<EmployeDmd> findEmployeDmdsById(List<Long> id);*/
    EmployeDmd createDmdUser (DmdUserDto dto);
    EmployeDmd annulerDmdUser (DmdUserDto dto);
    void createDmdParc(DmdParcDtoW dto);


    EmployeDmd updateDmdUser (DmdUserDto dto);
    EmployeDmd updateDmdParc (DmdParcDto dto);
    void updateDmdUserAttribution (DmdUserDtoZ dto);
    void delete(EmployeDmd employeDmd);

    //
    EmployeDmd validerDmd (Long id, Employe employe);
    EmployeDmd annulerDmd (Long id, EmployeDmd employeDmd);

}
