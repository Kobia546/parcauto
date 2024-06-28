package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Destination;
import ci.nkagou.parcauto.entities.Motif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotifRepository extends JpaRepository<Motif, Long> {
    Motif findByNomMotif(String nomMotif);
}
