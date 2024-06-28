package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailVehiculeChauffeurARepository extends JpaRepository<DetailVehiculeChauffeurA,Long> {
    List<DetailVehiculeChauffeurA> findAllByAttribution(Attribution attribution);
    //List <EmployeDmd> findByAttribution(Attribution attribution);


    //List<DetailVehiculeChauffeurA> findDetailVehiculeChauffeurAByDateBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}
