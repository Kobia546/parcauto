package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Typevehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypevehiculeRepository extends JpaRepository<Typevehicule, Long> {

    Typevehicule findByLibelle(String libelle);
}
