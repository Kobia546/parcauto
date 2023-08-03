package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeAssurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculePeriodeAssuranceRepository extends JpaRepository<VehiculePeriodeAssurance, Long> {

    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehicule(Vehicule vehicule);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByPeriode(Periode periode);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByAssurance_NomSociete(String nomSocieteAssurance);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehiculeAndPeriode(Vehicule vehicule, Periode periode);

}
