package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.EtatVehiculeDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDtoOut;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.VehiculeAttRepository;
import ci.nkagou.parcauto.repositories.VehiculeHistoriqueRepository;
import ci.nkagou.parcauto.repositories.VehiculeRepository;
import ci.nkagou.parcauto.services.VehiculeHistoriqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class VehiculeHistoriqueImpl implements VehiculeHistoriqueService {

    private VehiculeRepository vehiculeRepository;
    private VehiculeHistoriqueRepository vehiculeHistoriqueRepository;
    private VehiculeAttRepository vehiculeAttRepository;


    @Override
    public VehiculeHistorique dtoToVehicule(VehiculeHistoriqueDto dto) {
        VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();

        //Vehicule vehicule = vehiculeRepository.getById(dto.getVehicule());
        vehiculeHistorique.setStatutHistorique(dto.getStatutHistorique());
        vehiculeHistorique.setDateParcours(dto.getDateParcours());
        vehiculeHistorique.setVehiculeAtt(dto.getVehiculeAtt());

        return vehiculeHistorique;
    }

    @Override
    public VehiculeHistoriqueDtoOut vehiculeToDto(VehiculeHistorique vehiculeHistorique) {

        VehiculeHistoriqueDtoOut dto = new VehiculeHistoriqueDtoOut();

        dto.setId(vehiculeHistorique.getId());
        dto.setStatutHistorique(vehiculeHistorique.getStatutHistorique().toString());
        dto.setDateParcours(vehiculeHistorique.getDateParcours().toString().replace("T", " "));
        dto.setVehicule(vehiculeHistorique.getVehiculeAtt().getVehicule().getImmatriculation());

        List<DetailVehiculeA> detail = vehiculeHistorique.getVehiculeAtt().getDetailVehiculeA();
        StringBuilder concatenatedString = new StringBuilder();
        for (DetailVehiculeA element : detail) {
            String name = element.getEmployeDmd().getEmploye().toNomComplet();
            concatenatedString.append(name).append(", ");
        }

        dto.setNomEmploye(concatenatedString);

        List<DetailVehiculeA> details = vehiculeHistorique.getVehiculeAtt().getDetailVehiculeA();
        StringBuilder concatenatedStrings = new StringBuilder();
        for (DetailVehiculeA element : details) {
            String destination = element.getEmployeDmd().getDestination().getNomDestination();
            concatenatedStrings.append(destination).append(", ");
        }

        dto.setDestination(concatenatedStrings);

        return dto;
    }

    @Override
    public List<VehiculeHistoriqueDtoOut> listVehiculesToDto(List<VehiculeHistorique> vehiculeHistoriques) {
        List<VehiculeHistoriqueDtoOut> dtos = new ArrayList<>();
        for (VehiculeHistorique vehiculeHistorique : vehiculeHistoriques)
        {
            VehiculeHistoriqueDtoOut dto = new VehiculeHistoriqueDtoOut();

            dto = this.vehiculeToDto(vehiculeHistorique);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<VehiculeHistorique> all() {
        return vehiculeHistoriqueRepository.findAll();
    }

    @Override
    public VehiculeHistorique findById(Long id) {
         return vehiculeHistoriqueRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Vehicule introuvable avec l'identifiant :  " + id)
         );
    }

    @Override
    public void create(VehiculeHistoriqueDto dto) {

        VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();

        vehiculeHistorique.setId(dto.getId());
        vehiculeHistorique.setStatutHistorique(dto.getStatutHistorique());
        vehiculeHistorique.setDateParcours(dto.getDateParcours());
        vehiculeHistorique.setVehiculeAtt(dto.getVehiculeAtt());

        vehiculeHistoriqueRepository.save(vehiculeHistorique);
    }



    @Override
    public VehiculeHistorique update(VehiculeHistorique vehiculeHistorique) {
        return vehiculeHistoriqueRepository.save(vehiculeHistorique);
    }

    @Override
    public List<VehiculeAtt> getListVehiculeAttByVehicule(Vehicule vehicule) {
        return vehiculeAttRepository.findAllByVehicule(vehicule);
    }

    @Override
    public List<VehiculeAtt> getListVehiculeAttByVehicule(List<Vehicule> vehicules) {
        List<VehiculeAtt> vehiculeAtts = new ArrayList<>();
        for (Vehicule vehicule : vehicules){
            List<VehiculeAtt> v = this.getListVehiculeAttByVehicule(vehicule);
            vehiculeAtts.addAll(v);
        }
        return vehiculeAtts;
    }




    @Override
    public List<VehiculeHistorique> listVehiculeHistoriqueByDateBetweenAndVehiculeAttVehicule(EtatVehiculeDto dto) {
        return vehiculeHistoriqueRepository.findByDateParcoursBetweenAndVehiculeAttVehicule(dto.getDebut(),dto.getFin(),dto.getVehicule());
    }

    /*@Override
    public List<VehiculeHistorique> listVehiculeHistoriqueByDateBetweenAndVehiculeAttEmployeDmdEmploye(EtatVehiculeDto dto) {
        return vehiculeHistoriqueRepository.findByDateParcoursBetweenAndVehiculeAttEmployeDmdEmploye(dto.getDebut(),dto.getFin(),dto.getEmploye());
    }*/


}



