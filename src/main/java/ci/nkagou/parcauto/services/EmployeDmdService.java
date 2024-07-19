package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.EmployeEtatDto;
import ci.nkagou.parcauto.dtos.dmd.EmployeRapportDto;
import ci.nkagou.parcauto.dtos.dmd.EtatResponsableDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.RapportStatut;
import ci.nkagou.parcauto.enums.Statut;

import java.time.LocalDate;
import java.util.List;

public interface EmployeDmdService {



    List<EmployeDmd> all();
    EmployeDmd findById(Long id);
    //EmployeDmd findByLibelle(String libelle);
//    EmployeDmd create (EmployeDmd employeDmd);
//    void create(EmployeDmd employeDmd, AppUser userConnecte);
    EmployeDmd update (EmployeDmd employeDmd);
    //EmployeDmd updateTransport (EmployeDmd employeDmd);
    void delete(EmployeDmd employeDmd);
    List<EmployeDmd> getListEmployeDmdByDmd(Dmd dmd);
    List<EmployeDmd> getListEmployeDmdByDmd(List<Dmd> dmds);
    List<EmployeDmd> getListEmployeDmdByDmdAndStatut(List<Dmd> dmds, Statut statut);
    List<EmployeDmd> getListEmployeDmdByDmdAndEmploye(List<Dmd> dmds, Employe employe);
    List<EmployeDmd> getListEmployeDmdByDmdAndStatutAndDirection(List<Dmd> dmds,Statut statut,Direction direction);
    List<EmployeDmd> listEmployeDmdByDateBetweenAndStatut(LocalDate debut, LocalDate fin, Statut statut);
    List<EmployeDmd> listEmployeDmdByDateBetweenAndRapportStatut(EmployeRapportDto dto);
    List<EmployeDmd> listEmployeDmdByDateBetweenAndEmploye(EmployeEtatDto dto);
    List<EmployeDmd> listEmployeDmdByDateBetweenAndDirection(EtatResponsableDto dto);

}
