package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Periode;

import java.util.List;

public interface PeriodeService {

    List<Periode> all();
    Periode findById(Long id);
    Periode findByExercice(int exercice);
    Periode create (Periode periode);
    Periode update (Periode periode);
    void delete(Periode periode);
}
