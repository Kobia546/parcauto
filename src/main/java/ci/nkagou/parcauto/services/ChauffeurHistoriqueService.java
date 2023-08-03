package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDto;
import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDto;
import ci.nkagou.parcauto.dtos.vehiculeIndisponible.VehiculeHistoriqueDtoOut;
import ci.nkagou.parcauto.entities.ChauffeurHistorique;
import ci.nkagou.parcauto.entities.ChauffeurHistorique;

import java.util.List;

public interface ChauffeurHistoriqueService {
    ChauffeurHistorique dtoToChauffeurHistorique(ChauffeurHistoriqueDto dto);
    ChauffeurHistoriqueDtoOut chauffeurHistoriqueToDto (ChauffeurHistorique chauffeurHistorique);
    List<ChauffeurHistoriqueDtoOut> listChauffeurHistoriquesToDto(List<ChauffeurHistorique> chauffeurHistoriques);
    List<ChauffeurHistorique> all();
    ChauffeurHistorique findById(Long id);
    ChauffeurHistorique create(ChauffeurHistorique chauffeurHistorique);
    ChauffeurHistorique update(ChauffeurHistorique chauffeurHistorique);

}
