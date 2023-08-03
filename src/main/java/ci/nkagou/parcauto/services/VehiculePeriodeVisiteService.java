package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.vehiculeperiodevisite.VehiculePeriodeVisiteDtoOut;
import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeVisite;

import java.util.List;

public interface VehiculePeriodeVisiteService {

    VehiculePeriodeVisiteDtoOut VehiculePeriodeVisiteToDto(VehiculePeriodeVisite vehiculePeriodeVisite);
    List<VehiculePeriodeVisiteDtoOut> ListVehiculePeriodeVisiteToDto(List<VehiculePeriodeVisite> vehiculePeriodeVisites);

    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehicule(Vehicule vehicule);
    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByPeriode(Periode periode);
    List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehiculeAndPeriode(Vehicule vehicule, Periode periode);

    List<VehiculePeriodeVisite> all();
    VehiculePeriodeVisite findById(Long id);
    //VehiculePeriodeVisite findByLibelle(String libelle);
    VehiculePeriodeVisite create (VehiculePeriodeVisite vehiculePeriodeVisite);
    VehiculePeriodeVisite update (VehiculePeriodeVisite vehiculePeriodeVisite);
    void delete(VehiculePeriodeVisite vehiculePeriodeVisite);

}
