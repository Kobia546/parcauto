package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.vehiculeperiodevisite.VehiculePeriodeVisiteDtoOut;
import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeAssurance;
import ci.nkagou.parcauto.entities.VehiculePeriodeVisite;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.VehiculePeriodeVisiteRepository;
import ci.nkagou.parcauto.services.VehiculePeriodeVisiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class VehiculePeriodeVisiteServiceImpl implements VehiculePeriodeVisiteService {

    private VehiculePeriodeVisiteRepository vehiculePeriodeVisiteRepository;

    @Override
    public VehiculePeriodeVisiteDtoOut VehiculePeriodeVisiteToDto(VehiculePeriodeVisite vehiculePeriodeVisite   ) {

        VehiculePeriodeVisiteDtoOut dto = new VehiculePeriodeVisiteDtoOut();
        dto.setId(vehiculePeriodeVisite.getId());
        dto.setImmatriculation(vehiculePeriodeVisite.getVehicule().getImmatriculation());
        dto.setMarque(vehiculePeriodeVisite.getVehicule().getMarque().getName());
        dto.setExercice(vehiculePeriodeVisite.getPeriode().getExercice());
        dto.setNumVignette(vehiculePeriodeVisite.getVisitetechnique().getNumVignette());
        dto.setDateProchaineVisitTechn(vehiculePeriodeVisite.getVisitetechnique().getDateProchaineVisitTechn().toString());
        dto.setDateVisitTechn(vehiculePeriodeVisite.getVisitetechnique().getDateVisitTechn().toString());

        return dto;
    }

    @Override
    public List<VehiculePeriodeVisiteDtoOut> ListVehiculePeriodeVisiteToDto(List<VehiculePeriodeVisite> vehiculePeriodeVisites) {

        List<VehiculePeriodeVisiteDtoOut> dtos = new ArrayList<>();
        for (VehiculePeriodeVisite vehiculePeriodeVisite : vehiculePeriodeVisites){

            VehiculePeriodeVisiteDtoOut dto = new VehiculePeriodeVisiteDtoOut();
            dto = this.VehiculePeriodeVisiteToDto(vehiculePeriodeVisite);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehicule(Vehicule vehicule) {
        return vehiculePeriodeVisiteRepository.findVehiculePeriodeVisitesByVehicule(vehicule);
    }

    @Override
    public List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByPeriode(Periode periode) {
        return vehiculePeriodeVisiteRepository.findVehiculePeriodeVisitesByPeriode(periode);
    }

    @Override
    public List<VehiculePeriodeVisite> findVehiculePeriodeVisitesByVehiculeAndPeriode(Vehicule vehicule, Periode periode) {
        return vehiculePeriodeVisiteRepository.findVehiculePeriodeVisitesByVehiculeAndPeriode(vehicule, periode);
    }

    @Override
    public List<VehiculePeriodeVisite> all() {
        return vehiculePeriodeVisiteRepository.findAll();
    }

    @Override
    public VehiculePeriodeVisite findById(Long id) {

        return vehiculePeriodeVisiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("VehiculePeriodeVisite introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public VehiculePeriodeVisite create(VehiculePeriodeVisite vehiculePeriodeVisite) {
        return vehiculePeriodeVisiteRepository.save(vehiculePeriodeVisite);
    }

    @Override
    public VehiculePeriodeVisite update(VehiculePeriodeVisite vehiculePeriodeVisite) {
        return vehiculePeriodeVisiteRepository.save(vehiculePeriodeVisite);
    }

    @Override
    public void delete(VehiculePeriodeVisite vehiculePeriodeVisite) {
        vehiculePeriodeVisiteRepository.delete(vehiculePeriodeVisite);
    }
}
