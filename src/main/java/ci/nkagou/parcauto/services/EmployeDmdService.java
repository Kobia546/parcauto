package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.EmployeDmd;

import java.util.List;

public interface EmployeDmdService {



    List<EmployeDmd> all();
    EmployeDmd findById(Long id);
    //EmployeDmd findByLibelle(String libelle);
    EmployeDmd create (EmployeDmd employeDmd);
    EmployeDmd update (EmployeDmd employeDmd);
    void delete(EmployeDmd employeDmd);

}
