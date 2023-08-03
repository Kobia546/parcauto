package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.vehiculeperiodeassurance.VehiculePeriodeAssuranceDtoOut;
import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeAssurance;

import java.util.List;

public interface VehiculePeriodeAssuranceService {


    VehiculePeriodeAssuranceDtoOut VehiculePeriodeAssuranceToDto(VehiculePeriodeAssurance vehiculePeriodeAssurance);
    List<VehiculePeriodeAssuranceDtoOut>  ListVehiculePeriodeAssuranceToDto(List<VehiculePeriodeAssurance> vehiculePeriodeAssurances);

    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehicule(Vehicule vehicule);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByPeriode(Periode periode);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByAssurance_NomSociete(String nomSocieteAssurance);
    List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehiculeAndPeriode(Vehicule vehicule, Periode periode);


    List<VehiculePeriodeAssurance> all();
    VehiculePeriodeAssurance findById(Long id);
    //VehiculePeriodeAssurance findByLibelle(String libelle);
    VehiculePeriodeAssurance create (VehiculePeriodeAssurance vehiculePeriodeAssurance);
    VehiculePeriodeAssurance update (VehiculePeriodeAssurance vehiculePeriodeAssurance);
    void delete(VehiculePeriodeAssurance vehiculePeriodeAssurance);
}
