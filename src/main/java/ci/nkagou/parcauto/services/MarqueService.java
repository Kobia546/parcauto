package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Marque;

import java.util.List;

public interface MarqueService {

    List<Marque> all();
    Marque findById(Long id);
    Marque findByName(String nomMarque);
    Marque create (Marque marque);
    Marque update (Marque marque);
    void delete(Marque marque);
}
