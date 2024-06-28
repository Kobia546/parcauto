package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailCarburantARepository extends JpaRepository<DetailCarburantA,Long> {
    List<DetailCarburantA> findAllByAttribution(Attribution attribution);
    //List <EmployeDmd> findByAttribution(Attribution attribution);

    //List<DetailCarburantA> findDetailCarburantAByDateAttributionBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}
