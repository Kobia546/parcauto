package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Moyen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoyenRepository extends JpaRepository<Moyen, Long> {

    Moyen findByLibelle(String libelle);
}
