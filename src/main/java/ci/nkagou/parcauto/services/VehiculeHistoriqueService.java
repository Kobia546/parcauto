package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDtoOut;
import ci.nkagou.parcauto.entities.VehiculeHistorique;

import java.util.List;

public interface VehiculeHistoriqueService {

    VehiculeHistorique dtoToVehicule(VehiculeHistoriqueDto dto);
    VehiculeHistoriqueDtoOut vehiculeToDto (VehiculeHistorique vehiculeHistorique);
    List<VehiculeHistoriqueDtoOut> listVehiculesToDto(List<VehiculeHistorique> vehiculeHistoriques);
    List<VehiculeHistorique> all();
    VehiculeHistorique findById(Long id);
    void create (VehiculeHistoriqueDto dto);
    VehiculeHistorique update (VehiculeHistorique vehiculeHistorique);

}
