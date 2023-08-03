package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributionRepository extends JpaRepository<Attribution, Long> {

    //List<Attribution> findAttributionsByEmploye(Employe employe);

    List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe);


}
