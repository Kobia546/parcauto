package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuranceRepository extends JpaRepository<Assurance, Long> {

    Assurance findByNumPolice(String numeroPolice);
}
