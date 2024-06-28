package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.CarburantAtt;
import ci.nkagou.parcauto.entities.DetailVehiculeChauffeurA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CarburantAttRepository extends AttributionBaseRepository <CarburantAtt>{
//    List<CarburantAtt> findAllByAttribution(Attribution attribution);

}
