package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Visitetechnique;

import java.time.LocalDate;
import java.util.List;

public interface VisitetechniqueService {

    List<Visitetechnique> all();
    Visitetechnique getById(Long id);
    Visitetechnique findByVignette(String numVignette);
    Visitetechnique create (Visitetechnique visitetechnique);
    Visitetechnique update (Visitetechnique visitetechnique);
    void delete(Visitetechnique visitetechnique);
    List<Visitetechnique> findVisitetechniquesByDateVisitTechn(LocalDate date);
    List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechn(LocalDate date);
    List<Visitetechnique> findVisitetechniquesByDateProchaineVisitTechnBefore(LocalDate date);
}
