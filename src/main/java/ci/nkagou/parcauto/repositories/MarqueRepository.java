package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarqueRepository extends JpaRepository<Marque, Long> {

    Marque findByName(String name);
}
