package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.CarburantAtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CarburantAttRepository extends AttributionBaseRepository <CarburantAtt>{

}
