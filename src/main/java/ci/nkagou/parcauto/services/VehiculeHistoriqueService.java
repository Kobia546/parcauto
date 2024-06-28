package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.dmd.EtatVehiculeDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDtoOut;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.entities.VehiculeHistorique;
import ci.nkagou.parcauto.enums.Statut;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VehiculeHistoriqueService {

    VehiculeHistorique dtoToVehicule(VehiculeHistoriqueDto dto);
    VehiculeHistoriqueDtoOut vehiculeToDto (VehiculeHistorique vehiculeHistorique);
    List<VehiculeHistoriqueDtoOut> listVehiculesToDto(List<VehiculeHistorique> vehiculeHistoriques);
    List<VehiculeHistorique> all();
    VehiculeHistorique findById(Long id);
    void create (VehiculeHistoriqueDto dto);
    VehiculeHistorique update (VehiculeHistorique vehiculeHistorique);
    List<VehiculeAtt> getListVehiculeAttByVehicule(Vehicule vehicule);
    List<VehiculeAtt> getListVehiculeAttByVehicule(List<Vehicule> vehicule);
    //List<VehiculeAtt> ListVehiculeAttByDateBetween(EtatVehiculeDto dto);
    List<VehiculeHistorique> listVehiculeHistoriqueByDateBetweenAndVehiculeAttVehicule(EtatVehiculeDto dto);
    //List<VehiculeHistorique> listVehiculeHistoriqueByDateBetweenAndVehiculeAttEmployeDmdEmploye(EtatVehiculeDto dto);

}
