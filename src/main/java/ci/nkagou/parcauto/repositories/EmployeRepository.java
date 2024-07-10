package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Direction;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.enums.StatutChauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository  extends JpaRepository <Employe, Long> {

    
    Employe findByDirectionAndEstSuperieureHierachique(Direction direction, boolean estSuperieurHirachique);

    Employe findByNumMatEmpl (Long matricule);
//    long countByUser(AppUser user);
//    long countByUserAndStatut(AppUser user, Statut statut);

    Employe findByEmail(String email);
    List<Employe> findEmployesByEstUtilisateur (Boolean estUtilisateur);
    List<Employe> findEmployesByEstSuperieureHierachique(Boolean estSuperieur);
    Employe getNomByIdEmploye(String nom);
    Employe findByNomAndPrenom(String nom, String prenom);
    List<Employe> findEmployesByEstChauffeur (Boolean estChauffeur);
    List<Employe> findEmployesByEstChauffeurAndStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur);

    List<Employe> findAllByDirection(Direction direction);

    /*Employe findEmployeByAppUser(AppUser user);*/

    //List<Employe> findEmployesByEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur);


    //Employe getNomComplet();
}
