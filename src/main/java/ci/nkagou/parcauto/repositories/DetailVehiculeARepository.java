package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.DetailVehiculeA;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailVehiculeARepository extends JpaRepository<DetailVehiculeA,Long> {
    List<DetailVehiculeA> findAllByAttribution(Attribution attribution);
   // List <EmployeDmd> findByAttribution(Attribution attribution);
    //List<DetailVehiculeA> findDetailChauffeurAByDateBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

    //List<DetailVehiculeA> findDetailVehiculeAByDateAttributionBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}
