package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Moyen;

import java.util.List;

public interface MoyenService {

    List<Moyen> all();
    Moyen findById(Long id);
    Moyen findByLibelle(String nomMoyen);
    Moyen create (Moyen moyen);
    Moyen update (Moyen moyen);
    void delete(Moyen moyen);
}
