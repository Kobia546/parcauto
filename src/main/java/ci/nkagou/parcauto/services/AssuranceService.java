package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Assurance;

import java.util.List;

public interface AssuranceService {


    List<Assurance> all();
    Assurance findById(Long id);
    Assurance findByNumeroPolice(String numeroPolice);
    Assurance create (Assurance assurance);
    Assurance update (Assurance assurance);
    void delete(Assurance assurance);
}
