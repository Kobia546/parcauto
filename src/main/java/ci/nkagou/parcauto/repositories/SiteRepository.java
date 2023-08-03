package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository <Site, Long>{
    Site findByLibelle (String libelle);
}
