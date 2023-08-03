package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Visitetechnique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitetechniqueRepository  extends JpaRepository<Visitetechnique, Long> {

    Visitetechnique findByNumVignette(String numeroVignette);
    List<Visitetechnique> findVisitetechniquesByDateVisitTechn(LocalDate date);
    List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechn(LocalDate date);
    List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechnBefore(LocalDate date);
    //List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechnBetween(LocalDate date, LocalDate date1);

}
