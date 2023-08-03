package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeAssurance;
import ci.nkagou.parcauto.entities.VehiculePeriodeVisite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculePeriodeVisiteRepository extends JpaRepository<VehiculePeriodeVisite, Long> {

    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehicule(Vehicule vehicule);
    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByPeriode(Periode periode);
    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehiculeAndPeriode(Vehicule vehicule, Periode periode);
}
