package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.entities.ChauffeurHistorique;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.entities.Vehicule;
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
    void delete(Employe employe);
    String getNomComplet(Employe employe);
    List<Employe> listEmployesEstSuperieurHierachique(Boolean estResponsable);
    Employe findByNomPrenom(String nom, String prenom);
    List<Employe> findEmployesByEstChauffeur (Boolean estChauffeur);
    Employe disponible(Long id,Employe employe);
    Employe indisponible(Long id,Employe employe);
    List<Employe> findEmployesEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur);

    /*List<Employe> findEmployesByEstChauffeurStatutChauffeur (Boolean estChauffeur, StatutChauffeur statutChauffeur);*/





}
