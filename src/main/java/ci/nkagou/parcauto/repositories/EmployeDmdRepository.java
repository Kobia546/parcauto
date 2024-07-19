package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeDmdRepository extends JpaRepository<EmployeDmd, Long> {

    List<EmployeDmd> findEmployeDmdsByEmploye(Employe employe);

    List<EmployeDmd> findEmployeDmdsByStatut(Statut statut);

    EmployeDmd findByStatut(Statut statut);
    long countByStatut(Statut statut);
    long countByEmploye(Employe employe);
    long countByEmployeAndStatut(Employe employe, Statut statut);

    List<EmployeDmd> findEmployeDmdsByDmdMoyenDemandeAndStatut(MoyenDemande moyenDemande,Statut statut);

    /*List<EmployeDmd> findEmployeDmdsByStatutStatut(Statut statut, Statut statuts);*/

    List<EmployeDmd> findEmployeDmdsByStatutAndStatut(Statut statut, Statut statuts);

    EmployeDmd findByEmploye(Employe employe);

    List<EmployeDmd> findAllByDmd(Dmd dmd);

    //EmployeDmd findByIdAndStatut(Long id, Statut statut);

    /*List<EmployeDmd> findEmployeDmdsByDirection(Direction directionResponsable);*/

    /*List<EmployeDmd> findEmployeDmdsByEmployeAndDirection(Direction directionResponsable);*/

    /* List<EmployeDmd> findEmployeDmdsById(List<Long> id);*/

    /*List<EmployeDmd> findByDmdIn(List<Long> id);*/
}
