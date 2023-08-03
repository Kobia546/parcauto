package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository  extends JpaRepository<Direction, Long> {

    Direction findByLibelle(String libelleDirection);
}
