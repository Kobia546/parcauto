package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.entities.VehiculeChauffeurAtt;
import ci.nkagou.parcauto.enums.StatutAttrib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VehiculeChauffeurAttRepository extends AttributionBaseRepository <VehiculeChauffeurAtt> {

    //List<VehiculeAtt> AttributionVehiculeByStatutAttrib(StatutAttrib statutAttrib1,StatutAttrib statutAttrib);

    //List<VehiculeChauffeurAtt> AttributionVehiculeChauffeurByStatutAttrib(StatutAttrib statutAttrib1,StatutAttrib statutAttrib);
}
