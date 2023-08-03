package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Attribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface AttributionBaseRepository <T extends Attribution> extends JpaRepository<T,Long> {

}
