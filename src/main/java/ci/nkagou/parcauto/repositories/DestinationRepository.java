package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Article;
import ci.nkagou.parcauto.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Destination findByNomDestination(String nomDestination);
}
