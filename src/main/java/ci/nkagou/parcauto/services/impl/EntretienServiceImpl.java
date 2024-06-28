package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.entretien.DetailHerbdomadaireDto;
import ci.nkagou.parcauto.dtos.entretien.DetailVidangeDtoOut;
import ci.nkagou.parcauto.dtos.entretien.EntretienHerbdomadaireDto;
import ci.nkagou.parcauto.dtos.entretien.DetailHerbdomadaireDtoOut;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.EntretienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j

public class EntretienServiceImpl implements EntretienService {

    private EntretienHerbdomadaireRepository entretienHerbdomadaireRepository;
    private EntretienVidangeRepository entretienVidangeRepository;
    private VehiculeRepository vehiculeRepository;
    private DetailHerbdomadaireRepository detailHerbdomadaireRepository;
    private DetailVidangeRepository detailVidangeRepository;

    public EntretienServiceImpl(EntretienHerbdomadaireRepository entretienHerbdomadaireRepository,EntretienVidangeRepository entretienVidangeRepository,VehiculeRepository vehiculeRepository,DetailHerbdomadaireRepository detailHerbdomadaireRepository,DetailVidangeRepository detailVidangeRepository){
        this.entretienHerbdomadaireRepository = entretienHerbdomadaireRepository;
        this.entretienVidangeRepository = entretienVidangeRepository;
        this.detailHerbdomadaireRepository = detailHerbdomadaireRepository;
        this.detailVidangeRepository = detailVidangeRepository;
    }

    @Override
    public List<EntretienHerbdomadaire> allHerbdomadaire() {
        return entretienHerbdomadaireRepository.findAll();
    }

    @Override
    public List<EntretienVidange> allVidange() {
        return entretienVidangeRepository.findAll();
    }

    @Override
    public List<DetailHerbdomadaire> all() {
        return detailHerbdomadaireRepository.findAll();
    }

    @Override
    public List<DetailVidange> allEntretienVidange() {
        return detailVidangeRepository.findAll();
    }

    @Override
    public DetailHerbdomadaireDtoOut DetailHerbdomadaireDto(DetailHerbdomadaire detailHerbdomadaire) {
        DetailHerbdomadaireDtoOut dto = new DetailHerbdomadaireDtoOut();

        dto.setId(detailHerbdomadaire.getIdDetailHerbdomadaire());
        dto.setEstCarburant(detailHerbdomadaire.isEstCarburant()); // Convert boolean to string
        dto.setEstLavage(detailHerbdomadaire.isEstLavage());
        dto.setLitre(detailHerbdomadaire.getLitre());
        dto.setEntretien(detailHerbdomadaire.getEntretien());
        dto.setVehicule(detailHerbdomadaire.getVehicule().getImmatriculation());

        return dto;

    }

    @Override
    public List<DetailHerbdomadaireDtoOut> listEntretienDto(List<DetailHerbdomadaire> detailHerbdomadaires) {

        List<DetailHerbdomadaireDtoOut> dto = new ArrayList<>();

        for(DetailHerbdomadaire detailHerbdomadaire : detailHerbdomadaires) {

            DetailHerbdomadaireDtoOut dtos = new DetailHerbdomadaireDtoOut();
            dtos = this.DetailHerbdomadaireDto(detailHerbdomadaire);

            dto.add(dtos);
        }

        return dto;
    }

    @Override
    public DetailVidangeDtoOut DetailVidangeDto(DetailVidange detailVidange) {

        DetailVidangeDtoOut dto = new DetailVidangeDtoOut();

        dto.setId(detailVidange.getIdDetailVidange());
        dto.setMontant(detailVidange.getMontant());
        dto.setArticle(detailVidange.getArticle().getLibelle());
        dto.setEntretien(detailVidange.getEntretien());

        return dto;

    }

    @Override
    public List<DetailVidangeDtoOut> listVidangeDto(List<DetailVidange> detailVidanges) {

        List<DetailVidangeDtoOut> dto = new ArrayList<>();

        for(DetailVidange detailVidange : detailVidanges) {

            DetailVidangeDtoOut dtos = new DetailVidangeDtoOut();
            dtos = this.DetailVidangeDto(detailVidange);

            dto.add(dtos);
        }

        return dto;
    }


    @Override
    public void createEntretienHerbdomadaire(EntretienHerbdomadaireDto dto) {

        //Vehicule vehicules = vehiculeRepository.getById(dto.getId());
        EntretienHerbdomadaire entretienHerbdomadaire = new EntretienHerbdomadaire();

        entretienHerbdomadaire.setDateEntretien(dto.getDateEntretien());

        EntretienHerbdomadaire entretienHerbdomadaire1 = entretienHerbdomadaireRepository.save(entretienHerbdomadaire);

        List<DetailHerbdomadaireDto> dtos = dto.getDetailHerbdomadaireDto();


        for(DetailHerbdomadaireDto dtoss: dtos){

            DetailHerbdomadaire detailHerbdomadaire = new DetailHerbdomadaire();

            detailHerbdomadaire.setEstCarburant(dtoss.isEstCarburant());
            detailHerbdomadaire.setEstLavage(dtoss.isEstLavage());
            detailHerbdomadaire.setLitre(dtoss.getLitre());
            detailHerbdomadaire.setEntretien(entretienHerbdomadaire1);
            detailHerbdomadaire.setVehicule(dtoss.getVehicule());

            detailHerbdomadaireRepository.save(detailHerbdomadaire);

        }

    }

    @Override
    public List<EntretienVidange> findEntretienVidangeByVehicule(Vehicule vehicule) {
        return entretienVidangeRepository.findEntretienVidangeByVehicule(vehicule);
    }
}
