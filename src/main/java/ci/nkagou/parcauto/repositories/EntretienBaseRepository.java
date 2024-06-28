package ci.nkagou.parcauto.repositories;



import ci.nkagou.parcauto.entities.Entretien;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntretienBaseRepository <T extends Entretien> extends JpaRepository<T,Long> {
}
