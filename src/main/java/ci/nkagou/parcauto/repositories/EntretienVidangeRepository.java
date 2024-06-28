package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.EntretienVidange;
import ci.nkagou.parcauto.entities.Vehicule;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface EntretienVidangeRepository extends EntretienBaseRepository <EntretienVidange>{

    List<EntretienVidange> findEntretienVidangeByVehicule(Vehicule vehicule);
}
