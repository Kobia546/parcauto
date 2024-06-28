package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.DetailVehiculeARepository;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.services.DetailVehiculeAService;
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
public class DetailVehiculeAServiceImpl implements DetailVehiculeAService {

    private DetailVehiculeARepository detailVehiculeARepository;
    private EmployeRepository employeRepository;
    private AttributionRepository attributionRepository;

    /*private DetailVehiculeAServiceImpl(DetailVehiculeARepository detailVehiculeARepository,EmployeRepository employeRepository,AttributionRepository attributionRepository){
           this.detailVehiculeARepository = detailVehiculeARepository;
           this.employeRepository = employeRepository;
           this.attributionRepository = attributionRepository;
    }*/

    @Override
    public DetailVehiculeA findById(Long id) {
        return null;
    }

    /*@Override
    public List<DetailVehiculeA> listDetailVehiculeAByDateBetweenAndEmployeDmdEmploye(EtatAttributionEmployeDto dto) {
        Employe employe = employeRepository.getById(dto.getEmploye());
        return detailVehiculeARepository.findDetailVehiculeAByDateAttributionBetweenAndEmployeDmdEmploye(dto.getDebut(),dto.getFin(),employe);
    }*/

    @Override
    public DetailADateDtoOut DetailAttributionDto(DetailVehiculeA detail) {

        DetailADateDtoOut dto = new DetailADateDtoOut();

        dto.setId(detail.getIdDetailVehicule());
        dto.setDateAttribution(detail.getAttribution().getDateAttribution().toString().replace("T", " "));
        dto.setDateDeDepart(detail.getAttribution().getDateDeDepart() != null ? detail.getAttribution().getDateDeDepart().toString().replace("T", " ") : null);
        dto.setDateArrivee(detail.getAttribution().getDateArrivee() != null ? detail.getAttribution().getDateArrivee().toString().replace("T", " ") : null);
        //dto.setDateArrivee(detail.getAttribution().getDateArrivee().toString().replace("T", " "));
        dto.setNom(detail.getEmployeDmd().getEmploye().toNomComplet());
        dto.setTypeAttribution(detail.getAttribution().getTypeAttribution().toString());
        //dto.setStatutAttrib(detail.getAttribution().getStatutAttrib().toString());
        //dto.setDuration(detail.getAttribution().calculateDuration());
        dto.setStatutAttrib(detail.getAttribution().getStatutAttrib() != null ? detail.getAttribution().getStatutAttrib().toString() : null);
        dto.setDuration(detail.getAttribution().calculateDuration() != null ? detail.getAttribution().calculateDuration(): null);

        if (detail.getAttribution() instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) detail.getAttribution();
            dto.setStatutVehiculeA(vehiculeAtt.getStatutVehiculeA() != null ? vehiculeAtt.getStatutVehiculeA().toString() : null);
            //dto.setNomComplet(attribution.getDetailVehiculeA() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);
            dto.setMotif(vehiculeAtt.getMotif() != null ? vehiculeAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeAtt.getObservation() != null ? vehiculeAtt.getObservation() : null);
            dto.setVehicule(vehiculeAtt.getVehicule().getImmatriculation() != null ? vehiculeAtt.getVehicule().getImmatriculation() : null);

        }

        /*if (detail.getAttribution() instanceof VehiculeChauffeurAtt) {
            VehiculeChauffeurAtt vehiculeChauffeurAtt = (VehiculeChauffeurAtt) detail.getAttribution();
            dto.setStatutVehiculeA(vehiculeChauffeurAtt.getStatutVehiculeA() != null ? vehiculeChauffeurAtt.getStatutVehiculeA().toString() : null);
            dto.setMotif(vehiculeChauffeurAtt.getMotif() != null ? vehiculeChauffeurAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeChauffeurAtt.getObservation() != null ? vehiculeChauffeurAtt.getObservation() : null);
            dto.setVehicule(vehiculeChauffeurAtt.getVehicule().getImmatriculation() != null ? vehiculeChauffeurAtt.getVehicule().getImmatriculation() : null);
            dto.setEmploye(vehiculeChauffeurAtt.getEmploye().toNomComplet() != null ? vehiculeChauffeurAtt.getEmploye().toNomComplet() : null);
            dto.setStatutChauffeurA(vehiculeChauffeurAtt.getStatutChauffeurA() != null ? vehiculeChauffeurAtt.getStatutChauffeurA().toString() : null);
            dto.setMotifChauffeur(vehiculeChauffeurAtt.getMotifChauffeur() != null ? vehiculeChauffeurAtt.getMotifChauffeur().toString() : null);
            dto.setObservationChauffeur(vehiculeChauffeurAtt.getObservationChauffeur() != null ? vehiculeChauffeurAtt.getObservationChauffeur() : null);
        }*/

        return dto;
    }

    @Override
    public List<DetailADateDtoOut> listDetailAttributionDto(List<DetailVehiculeA> detailVehiculeA) {

        List<DetailADateDtoOut> dto = new ArrayList<>();

        for(DetailVehiculeA detail: detailVehiculeA){
            DetailADateDtoOut dtoss = new DetailADateDtoOut();
            dtoss = this.DetailAttributionDto(detail);
            dto.add(dtoss);
        }
        return dto;
    }

    @Override
    public List<DetailVehiculeA> getListDetailVehiculeAByAttribution(Attribution attribution) {
        return detailVehiculeARepository.findAllByAttribution(attribution);
    }

    @Override
    public List<DetailVehiculeA> getListDetailVehiculeAByListAttribution(List<Attribution> attributions) {

        List<DetailVehiculeA> detail = new ArrayList<>();

        for(Attribution attribution: attributions){
            List<DetailVehiculeA> details = this.getListDetailVehiculeAByAttribution(attribution);
            detail.addAll(details);
        }
        return detail;
    }

    @Override
    public List<DetailVehiculeA> getListDetailVehiculeAByListAttributionEmploye(List<Attribution> attribution, Employe employe) {
        List<DetailVehiculeA> details = this.getListDetailVehiculeAByListAttribution(attribution);
        List<DetailVehiculeA> detail1 = new ArrayList<>();

        for(DetailVehiculeA d: details){
            if(d.getEmployeDmd().getEmploye().equals(employe)) {
                detail1.add(d);
            }
        }
        return detail1;
    }

    @Override
    public List<DetailVehiculeA> listDetailVehiculeAByDateBetween(EtatAttributionEmployeDto dto) {
        List<Attribution> attribution = attributionRepository.findAllByDateAttributionBetween(dto.getDebut(),dto.getFin());
        Employe employe = employeRepository.getById(dto.getEmploye());
        List<DetailVehiculeA> detail = new ArrayList<>();

        if(dto.getEmploye() != 0L) {
            detail = this.getListDetailVehiculeAByListAttributionEmploye(attribution, employe);
        } else if(dto.getEmploye() == 0L) {
            detail = this.getListDetailVehiculeAByListAttribution(attribution);
        }
        return detail;
    }


}
