package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Dmd;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface DmdRepository extends JpaRepository<Dmd, Long> {

    List<Dmd> findAllByDatePrevueBetween(LocalDate debut, LocalDate fin);

    //List<Dmd> findByDateRemplissageDmd(LocalDate dateRemplissage);
    //List<Dmd> findDmdsByDateRemplissageDmd(LocalDate dateRemplissage);
   // List<Dmd> findDmdsByMotifDmd(MotifDmd  motifDmd);

    //List<Dmd> findDmdsByEmploye (Employe employe);
}
