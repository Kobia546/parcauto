package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Dmd;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DmdRepository extends JpaRepository<Dmd, Long> {


    //List<Dmd> findByDateRemplissageDmd(LocalDate dateRemplissage);
    //List<Dmd> findDmdsByDateRemplissageDmd(LocalDate dateRemplissage);
   // List<Dmd> findDmdsByMotifDmd(MotifDmd  motifDmd);

    //List<Dmd> findDmdsByEmploye (Employe employe);
}
