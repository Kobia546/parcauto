package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportEmployeDto;
import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutChauffeur;

import java.util.List;

public interface EmployeService {

    List<Employe> all();
    Employe findById(Long id);
    EmployeDtoOut employeToDto (Employe employe);
    List<EmployeDtoOut> listEmployesToDto(List<Employe> employes);
    List<Employe> findEmployesByEstUtilisateur (Boolean estUtilisateur);
    Employe findByMatricule(Long matricule);
    Employe findByEmail(String email);
    Employe create (Employe employe);
    Employe update (Employe employe);
    Employe getEmployeByUserName(String username);
    /*Employe findEmployeByAppUser(AppUser user);*/
    void delete(Employe employe);
    String getNomComplet(Employe employe);
    List<Employe> listEmployesEstSuperieurHierachique(Boolean estResponsable);
    Employe findByNomPrenom(String nom, String prenom);
    List<Employe> findEmployesByEstChauffeur (Boolean estChauffeur);
    Employe disponible(Long id,Employe employe);
    Employe indisponible(Long id,Employe employe);
    List<Employe> findEmployesEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur);
    Employe findByDirectionEstSuperieurHirarchique(Direction direction,boolean estSuperieurHirarchique);
    RapportChauffeurDto asDto(Employe employe);
    RapportEmployeDto asDtos(Employe employe);
    List<RapportChauffeurDto> listRapportChauffeur(List<Employe> employe);
    List<RapportEmployeDto> listRapportEmploye(List<Employe> employe);

    /*List<Employe> findEmployesByEstChauffeurStatutChauffeur (Boolean estChauffeur, StatutChauffeur statutChauffeur);*/


}
