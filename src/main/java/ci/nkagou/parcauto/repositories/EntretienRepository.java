package ci.nkagou.parcauto.repositories;


import ci.nkagou.parcauto.entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntretienRepository extends JpaRepository<Entretien, Long> {
}
