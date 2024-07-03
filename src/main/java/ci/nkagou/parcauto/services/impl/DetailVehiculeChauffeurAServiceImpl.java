package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDto;
import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.DetailVehiculeChauffeurARepository;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.services.DetailVehiculeChauffeurAService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DetailVehiculeChauffeurAServiceImpl implements DetailVehiculeChauffeurAService {

    private DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository;
    private EmployeRepository employeRepository;
    private EmployeDmdRepository employeDmdRepository;

    private AttributionRepository attributionRepository;

    /*private DetailVehiculeChauffeurAServiceImpl(DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository,EmployeRepository employeRepository,AttributionRepository attributionRepository){
        this.detailVehiculeChauffeurARepository = detailVehiculeChauffeurARepository;
        this.employeRepository = employeRepository;
        this.attributionRepository = attributionRepository;
    }*/



   /* @Override
    public List<DetailVehiculeChauffeurA> listDetailVehiculeChauffeurAByDateBetweenAndEmployeDmdEmploye(EtatAttributionEmployeDto dto) {
        Employe employe = employeRepository.getById(dto.getEmploye());
        return detailVehiculeChauffeurARepository.findDetailVehiculeChauffeurAByDateAttributionBetweenAndEmployeDmdEmploye(dto.getDebut(),dto.getFin(),employe);
    }*/


    @Override
    public List<DetailVehiculeChauffeurA> All() {
        return detailVehiculeChauffeurARepository.findAll();
    }

    @Override
    public DetailADateDtoOut DetailAttributionDto(DetailVehiculeChauffeurA detail) {

        DetailADateDtoOut dto = new DetailADateDtoOut();

        dto.setId(detail.getIdDetailChauffeur());
        dto.setDateAttribution(detail.getAttribution().getDateAttribution().toString().replace("T", " "));
        //dto.setDateDeDepart(detail.getAttribution().getDateDeDepart().toString().replace("T", " "));
        dto.setDateDeDepart(detail.getAttribution().getDateDeDepart() != null ? detail.getAttribution().getDateDeDepart().toString().replace("T", " ") : null);
        dto.setDateArrivee(detail.getAttribution().getDateArrivee() != null ? detail.getAttribution().getDateArrivee().toString().replace("T", " ") : null);
        //dto.setDateArrivee(detail.getAttribution().getDateArrivee().toString().replace("T", " "));
        dto.setNom(detail.getEmployeDmd().getEmploye().toNomComplet());
        dto.setTypeAttribution(detail.getAttribution().getTypeAttribution().toString());
        //dto.setStatutAttrib(detail.getAttribution().getStatutAttrib().toString());
        //dto.setDuration(detail.getAttribution().calculateDuration());
        dto.setStatutAttrib(detail.getAttribution().getStatutAttrib() != null ? detail.getAttribution().getStatutAttrib().toString() : null);
        dto.setDuration(detail.getAttribution().calculateDuration() != null ? detail.getAttribution().calculateDuration(): null);

        /*if (detail.getAttribution() instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) detail.getAttribution();
            dto.setStatutVehiculeA(vehiculeAtt.getStatutVehiculeA() != null ? vehiculeAtt.getStatutVehiculeA().toString() : null);
            //dto.setNomComplet(attribution.getDetailVehiculeA() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);
            dto.setMotif(vehiculeAtt.getMotif() != null ? vehiculeAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeAtt.getObservation() != null ? vehiculeAtt.getObservation() : null);
            dto.setVehicule(vehiculeAtt.getVehicule().getImmatriculation() != null ? vehiculeAtt.getVehicule().getImmatriculation() : null);

        }*/

        if (detail.getAttribution() instanceof VehiculeChauffeurAtt) {
            VehiculeChauffeurAtt vehiculeChauffeurAtt = (VehiculeChauffeurAtt) detail.getAttribution();
            dto.setStatutVehiculeA(vehiculeChauffeurAtt.getStatutVehiculeA() != null ? vehiculeChauffeurAtt.getStatutVehiculeA().toString() : null);
            dto.setMotif(vehiculeChauffeurAtt.getMotif() != null ? vehiculeChauffeurAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeChauffeurAtt.getObservation() != null ? vehiculeChauffeurAtt.getObservation() : null);
            dto.setVehicule(vehiculeChauffeurAtt.getVehicule().getImmatriculation() != null ? vehiculeChauffeurAtt.getVehicule().getImmatriculation() : null);
            dto.setEmploye(vehiculeChauffeurAtt.getEmploye().toNomComplet() != null ? vehiculeChauffeurAtt.getEmploye().toNomComplet() : null);
            dto.setStatutChauffeurA(vehiculeChauffeurAtt.getStatutChauffeurA() != null ? vehiculeChauffeurAtt.getStatutChauffeurA().toString() : null);
            dto.setMotifChauffeur(vehiculeChauffeurAtt.getMotifChauffeur() != null ? vehiculeChauffeurAtt.getMotifChauffeur().toString() : null);
            dto.setObservationChauffeur(vehiculeChauffeurAtt.getObservationChauffeur() != null ? vehiculeChauffeurAtt.getObservationChauffeur() : null);
        }

        return dto;
    }

    @Override
    public List<DetailADateDtoOut> listDetailAttributionDto(List<DetailVehiculeChauffeurA> detailVehiculeChauffeurA) {

        List<DetailADateDtoOut> dto = new ArrayList<>();

        for(DetailVehiculeChauffeurA detail: detailVehiculeChauffeurA){
            DetailADateDtoOut dtoss = new DetailADateDtoOut();
            dtoss = this.DetailAttributionDto(detail);
            dto.add(dtoss);
        }
        return dto;
    }

    @Override
    public List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByAttribution(Attribution attribution) {
        return detailVehiculeChauffeurARepository.findAllByAttribution(attribution);
    }

    @Override
    public List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByListAttribution(List<Attribution> attributions) {

        List<DetailVehiculeChauffeurA> detail = new ArrayList<>();

        for(Attribution attribution: attributions){
            List<DetailVehiculeChauffeurA> details = this.getListDetailVehiculeChauffeurAByAttribution(attribution);
            detail.addAll(details);
        }
        return detail;
    }

    @Override
    public List<DetailVehiculeChauffeurA> getListDetailVehiculeChauffeurAByListAttributionEmploye(List<Attribution> attribution, Employe employe) {
        List<DetailVehiculeChauffeurA> details = this.getListDetailVehiculeChauffeurAByListAttribution(attribution);
        List<DetailVehiculeChauffeurA> detail1 = details.stream()
                .filter(d -> d.getEmployeDmd().getEmploye().equals(employe))
                .collect(Collectors.toList());
        return detail1;
    }


    @Override
    public List<DetailVehiculeChauffeurA> listDetailVehiculeChauffeurAByDateBetween(EtatAttributionEmployeDto dto) {

        List<Attribution> attribution = attributionRepository.findAllByDateAttributionBetween(dto.getDebut(),dto.getFin());
        List<DetailVehiculeChauffeurA> detail = new ArrayList<>();
        Employe employe = employeRepository.getById(dto.getEmploye());

        if(dto.getEmploye() != 0L) {
            detail = this.getListDetailVehiculeChauffeurAByListAttributionEmploye(attribution, employe);
        } else if(dto.getEmploye() == 0L) {
            detail = this.getListDetailVehiculeChauffeurAByListAttribution(attribution);
        }

        return detail;
    }


    /*@Override
    public List<DetailADateDto> listDetailAttributionByDateAttributionBetweenEmployeDmdEmploye(EtatAttributionEmployeDto dto) {
        return detailVehiculeChauffeurARepository.findDetailAttributionByDateAttributionBetweenEmployeDmdEmploye(dto.getDebut(),dto.getFin(),dto.getEmploye());
    }*/

}
