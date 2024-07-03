package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDto;
import ci.nkagou.parcauto.dtos.chauffeurhistorique.ChauffeurHistoriqueDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatChauffeurDto;
import ci.nkagou.parcauto.entities.ChauffeurHistorique;
import ci.nkagou.parcauto.entities.DetailVehiculeChauffeurA;
import ci.nkagou.parcauto.repositories.ChauffeurHistoriqueRepository;
import ci.nkagou.parcauto.services.ChauffeurHistoriqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ChauffeurHistoriqueServiceImpl implements ChauffeurHistoriqueService {

    private ChauffeurHistoriqueRepository chauffeurHistoriqueRepository;

    @Override
    public ChauffeurHistorique dtoToChauffeurHistorique(ChauffeurHistoriqueDto dto) {

        ChauffeurHistorique chauffeurHistorique;

        if (dto.getId() != null) {
            // Récupérer l'objet existant à partir de la base de données
            chauffeurHistorique = chauffeurHistoriqueRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("ID not found"));
        } else {
            // Créer un nouvel objet
            chauffeurHistorique = new ChauffeurHistorique();
        }
        chauffeurHistorique.setStatutHistorique(dto.getStatutHistorique());
        chauffeurHistorique.setDateParcours(dto.getDateParcours());
        chauffeurHistorique.setVehiculeChauffeurAtt(dto.getVehiculeChauffeurAtt());


        ChauffeurHistorique chauffeurHistorique1 = chauffeurHistoriqueRepository.save(chauffeurHistorique);
        return chauffeurHistorique1;
    }

    @Override
    public ChauffeurHistoriqueDtoOut chauffeurHistoriqueToDto(ChauffeurHistorique chauffeurHistorique) {

        ChauffeurHistoriqueDtoOut dto = new ChauffeurHistoriqueDtoOut();

        dto.setId(chauffeurHistorique.getId());
        dto.setStatutHistorique(chauffeurHistorique.getStatutHistorique().toString());
        dto.setDateParcours(chauffeurHistorique.getDateParcours().toString().replace("T", " "));

        dto.setEmploye(chauffeurHistorique.getVehiculeChauffeurAtt().getEmploye().toNomComplet());
        dto.setVehicule(chauffeurHistorique.getVehiculeChauffeurAtt().getVehicule().getImmatriculation());

        List<DetailVehiculeChauffeurA> detail = chauffeurHistorique.getVehiculeChauffeurAtt().getDetailVehiculeChauffeurA();
        StringBuilder concatenatedString = new StringBuilder();
        for (DetailVehiculeChauffeurA element : detail) {
            String name = element.getEmployeDmd().getEmploye().toNomComplet();
            concatenatedString.append(name).append(", ");
        }

        dto.setNomEmploye(concatenatedString);

        List<DetailVehiculeChauffeurA> details = chauffeurHistorique.getVehiculeChauffeurAtt().getDetailVehiculeChauffeurA();
        StringBuilder concatenatedStrings = new StringBuilder();
        for (DetailVehiculeChauffeurA element : details) {
            String destination = element.getEmployeDmd().getDestination().getNomDestination();
            concatenatedStrings.append(destination).append(", ");
        }

        dto.setDestination(concatenatedStrings);


        return dto;
    }


    @Override
    public List<ChauffeurHistoriqueDtoOut> listChauffeurHistoriquesToDto(List<ChauffeurHistorique> chauffeurHistoriques) {

        List<ChauffeurHistoriqueDtoOut> dto = new ArrayList<>();

        for(ChauffeurHistorique chauffeurHistorique: chauffeurHistoriques){

            ChauffeurHistoriqueDtoOut chauffeurHistorique1 = new ChauffeurHistoriqueDtoOut();
            chauffeurHistorique1 = this.chauffeurHistoriqueToDto(chauffeurHistorique);
            dto.add(chauffeurHistorique1);

        }
        return dto;
    }

    @Override
    public List<ChauffeurHistorique> all() {
        return chauffeurHistoriqueRepository.findAll();
    }

    @Override
    public ChauffeurHistorique findById(Long id) {
        return chauffeurHistoriqueRepository.getById(id);
    }

    @Override
    public ChauffeurHistorique create(ChauffeurHistorique chauffeurHistorique) {
        return chauffeurHistoriqueRepository.save(chauffeurHistorique);
    }

    @Override
    public ChauffeurHistorique update(ChauffeurHistorique chauffeurHistorique) {
        return chauffeurHistoriqueRepository.save(chauffeurHistorique);
    }

    @Override
    public List<ChauffeurHistorique> listChauffeurHistoriqueByDateBetweenAndVehiculeChauffeurAttVehicule(EtatChauffeurDto dto) {
        return chauffeurHistoriqueRepository.findByDateParcoursBetweenAndVehiculeChauffeurAttVehicule(dto.getDebut(),dto.getFin(),dto.getVehicule());
    }

    @Override
    public List<ChauffeurHistorique> listChauffeurHistoriqueByDateBetweenAndVehiculeChauffeurAttEmploye(EtatChauffeurDto dto) {
        return chauffeurHistoriqueRepository.findByDateParcoursBetweenAndVehiculeChauffeurAttEmploye(dto.getDebut(),dto.getFin(),dto.getEmploye());
    }


}
