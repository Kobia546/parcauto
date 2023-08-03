package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeDmdRepository extends JpaRepository<EmployeDmd, Long> {

    List<EmployeDmd> findEmployeDmdsByEmploye(Employe employe);

    List<EmployeDmd> findEmployeDmdsByStatut(Statut statut);

    List<EmployeDmd> findEmployeDmdsByDmdMoyenDemandeAndStatut(MoyenDemande moyenDemande,Statut statut);

    /* List<EmployeDmd> findEmployeDmdsById(List<Long> id);*/

    /*List<EmployeDmd> findByDmdIn(List<Long> id);*/
}
