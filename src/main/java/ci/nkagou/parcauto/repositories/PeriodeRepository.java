package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Periode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodeRepository extends JpaRepository<Periode, Long> {

    Periode findByExercice(int exercice);
}
