package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.employe.EmployeRequest;
import ci.nkagou.parcauto.dtos.employe.EmployeResponse;
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

//    Employe createOrUpdateEmploye(EmployeFormDto employeFormDto);

    Employe createOrUpdateEmploye(EmployeDtoOut employeFormDto);

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
    //Chauffeur
    Employe disponible(Long id,Employe employe);
    //Chauffeur
    Employe indisponible(Long id,Employe employe);
    List<Employe> findEmployesEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur);
    Employe findByDirectionEstSuperieurHirarchique(Direction direction,boolean estSuperieurHirarchique);
    RapportChauffeurDto asDto(Employe employe);
    RapportEmployeDto asDtos(Employe employe);
    List<RapportChauffeurDto> listRapportChauffeur(List<Employe> employe);
    List<RapportEmployeDto> listRapportEmploye(List<Employe> employe);

    //List des employes (Superieur Hierachique) appartenant a une direction et est superieur hierachique en fonction d'un employé
    List<Employe> listSuperieurByEmploye(Employe employe);

    List<String> listEmailsSuperieur(Employe employe);

    //List des employes ayant le role de responsable Parc auto
    List<Employe> listParcAuto ();

    List<Employe> listMoyenGeneraux();

    List<String> listEmailByListEmploye(List<Employe> employes);

    //////////////////
    EmployeResponse DTO (Employe employe);
    Employe ENTITY (EmployeRequest request);

    List<EmployeResponse> DTOS (List<Employe> employes);
    List<EmployeResponse> DTOS ();

    void create(EmployeRequest request);

    void update (EmployeRequest request, Long idEmploye);

    Employe getEmploye(Long id);

    void delete (Long id);


    /*List<Employe> findEmployesByEstChauffeurStatutChauffeur (Boolean estChauffeur, StatutChauffeur statutChauffeur);*/


}
