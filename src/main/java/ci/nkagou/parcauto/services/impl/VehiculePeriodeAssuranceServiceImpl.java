package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.vehiculeperiodeassurance.VehiculePeriodeAssuranceDtoOut;
import ci.nkagou.parcauto.entities.Periode;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculePeriodeAssurance;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.VehiculePeriodeAssuranceRepository;
import ci.nkagou.parcauto.services.VehiculePeriodeAssuranceService;
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
public class VehiculePeriodeAssuranceServiceImpl implements VehiculePeriodeAssuranceService {

    private VehiculePeriodeAssuranceRepository vehiculePeriodeAssuranceRepository;


    @Override
    public VehiculePeriodeAssuranceDtoOut VehiculePeriodeAssuranceToDto(VehiculePeriodeAssurance vehiculePeriodeAssurance) {

        VehiculePeriodeAssuranceDtoOut dto = new VehiculePeriodeAssuranceDtoOut();
        dto.setId(vehiculePeriodeAssurance.getId());
        dto.setImmatriculation(vehiculePeriodeAssurance.getVehicule().getImmatriculation());
        dto.setMarque(vehiculePeriodeAssurance.getVehicule().getMarque().getName());
        dto.setExercice(vehiculePeriodeAssurance.getPeriode().getExercice());
        dto.setNumeroPolice(vehiculePeriodeAssurance.getAssurance().getNumPolice());
        dto.setSociete(vehiculePeriodeAssurance.getAssurance().getNomSociete());
        dto.setDebutContrat(vehiculePeriodeAssurance.getAssurance().getDateDebutContrat().toString());
        dto.setFinContrat(vehiculePeriodeAssurance.getAssurance().getDateFinContrat().toString());
        return dto;
    }

    @Override
    public List<VehiculePeriodeAssuranceDtoOut> ListVehiculePeriodeAssuranceToDto(List<VehiculePeriodeAssurance> vehiculePeriodeAssurances) {

        List<VehiculePeriodeAssuranceDtoOut> dtos = new ArrayList<>();

        for (VehiculePeriodeAssurance vehiculePeriodeAssurance : vehiculePeriodeAssurances){
            VehiculePeriodeAssuranceDtoOut dto = new VehiculePeriodeAssuranceDtoOut();
            dto = this.VehiculePeriodeAssuranceToDto(vehiculePeriodeAssurance);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehicule(Vehicule vehicule) {
        return vehiculePeriodeAssuranceRepository.findVehiculePeriodeAssurancesByVehicule(vehicule);
    }

    @Override
    public List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByPeriode(Periode periode) {
        return vehiculePeriodeAssuranceRepository.findVehiculePeriodeAssurancesByPeriode(periode);
    }

    @Override
    public List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByAssurance_NomSociete(String nomSocieteAssurance) {
        return vehiculePeriodeAssuranceRepository.findVehiculePeriodeAssurancesByAssurance_NomSociete(nomSocieteAssurance);
    }

    @Override
    public List<VehiculePeriodeAssurance> findVehiculePeriodeAssurancesByVehiculeAndPeriode(Vehicule vehicule, Periode periode) {
        return vehiculePeriodeAssuranceRepository.findVehiculePeriodeAssurancesByVehiculeAndPeriode(vehicule, periode);
    }

    @Override
    public List<VehiculePeriodeAssurance> all() {
        return vehiculePeriodeAssuranceRepository.findAll();
    }

    @Override
    public VehiculePeriodeAssurance findById(Long id) {

        return vehiculePeriodeAssuranceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("VehiculePeriodeAssurance introuvable avec l'identifiant :  " + id)
        );
    }

/*    @Override
    public VehiculePeriodeAssurance findByLibelle(String libelle) {
        return null;
    }*/

    @Override
    public VehiculePeriodeAssurance create(VehiculePeriodeAssurance vehiculePeriodeAssurance) {
        return vehiculePeriodeAssuranceRepository.save(vehiculePeriodeAssurance);
    }

    @Override
    public VehiculePeriodeAssurance update(VehiculePeriodeAssurance vehiculePeriodeAssurance) {
        return vehiculePeriodeAssuranceRepository.save(vehiculePeriodeAssurance);
    }

    @Override
    public void delete(VehiculePeriodeAssurance vehiculePeriodeAssurance) {
        vehiculePeriodeAssuranceRepository.delete(vehiculePeriodeAssurance);
    }
}
