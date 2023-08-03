package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Moyen;
import ci.nkagou.parcauto.entities.Typevehicule;

import java.util.List;

public interface TypevehiculeService {

    List<Typevehicule> all();
    Typevehicule findById(Long id);
    Typevehicule findByLibelle(String libelle);
    Typevehicule create (Typevehicule typevehicule);
    Typevehicule update (Typevehicule typevehicule);
    void delete(Typevehicule typevehicule);
}
