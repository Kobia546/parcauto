package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.entretien.*;
import ci.nkagou.parcauto.entities.*;

import java.util.List;

public interface EntretienService {


    List<EntretienHerbdomadaire> allHerbdomadaire();
    List<EntretienVidange> allVidange();
    List<DetailHerbdomadaire> all();
    List<DetailVidange> allEntretienVidange();
    DetailHerbdomadaireDtoOut DetailHerbdomadaireDto(DetailHerbdomadaire detailHerbdomadaire);
    List<DetailHerbdomadaireDtoOut> listEntretienDto(List<DetailHerbdomadaire> detailHerbdomadaire);
    DetailVidangeDtoOut DetailVidangeDto(DetailVidange detailVidange);
    List<DetailVidangeDtoOut> listVidangeDto(List<DetailVidange> detailVidange);
    void createEntretienHerbdomadaire(EntretienHerbdomadaireDto dto);
    List<EntretienVidange> findEntretienVidangeByVehicule(Vehicule vehicule);

}
