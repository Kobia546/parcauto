package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.DetailADateDtoOut;
import ci.nkagou.parcauto.dtos.dmd.EtatAttributionEmployeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.DetailCarburantARepository;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.services.DetailCarburantAService;
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
public class DetailCarburantAServiceImpl implements DetailCarburantAService {

    private DetailCarburantARepository detailCarburantARepository;
    private EmployeRepository employeRepository;
    private AttributionRepository attributionRepository;

    /*private DetailCarburantAServiceImpl(DetailCarburantARepository detailCarburantARepository,EmployeRepository employeRepository,AttributionRepository attributionRepository){
            this.detailCarburantARepository = detailCarburantARepository;
            this.employeRepository = employeRepository;
            this.attributionRepository = attributionRepository;
    }*/


    @Override
    public DetailCarburantA findById(Long id) {
        return null;
    }

    /*@Override
    public List<DetailCarburantA> listDetailCarburantAByDateBetweenAndEmployeDmdEmploye(EtatAttributionEmployeDto dto) {
        Employe employe = employeRepository.getById(dto.getEmploye());
        return detailCarburantARepository.findDetailCarburantAByDateAttributionBetweenAndEmployeDmdEmploye(dto.getDebut(),dto.getFin(),employe);
    }*/

    @Override
    public DetailADateDtoOut DetailAttributionDto(DetailCarburantA detail) {

        DetailADateDtoOut dto = new DetailADateDtoOut();

        dto.setId(detail.getIdDetailCarburant());
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

        /*if (detail.getAttribution() instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) detail.getAttribution();
            dto.setStatutVehiculeA(vehiculeAtt.getStatutVehiculeA() != null ? vehiculeAtt.getStatutVehiculeA().toString() : null);
            //dto.setNomComplet(attribution.getDetailVehiculeA() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);
            dto.setMotif(vehiculeAtt.getMotif() != null ? vehiculeAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeAtt.getObservation() != null ? vehiculeAtt.getObservation() : null);
            dto.setVehicule(vehiculeAtt.getVehicule().getImmatriculation() != null ? vehiculeAtt.getVehicule().getImmatriculation() : null);

        }*/

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

        if (detail.getAttribution() instanceof CarburantAtt) {
            CarburantAtt carburantAtt = (CarburantAtt) detail.getAttribution();
//            dto.setRecuCarburant(carburantAtt.getRecuCarburant() != null ?  carburantAtt.getRecuCarburant() : null);
            dto.setMontant(carburantAtt.getMontant() != 0 ? carburantAtt.getMontant() : 0);
//            dto.setLitre(carburantAtt.getLitre() != 0 ? carburantAtt.getLitre() : 0);
            dto.setVehicule(carburantAtt.getImmatriculationVehicule() != null ? carburantAtt.getImmatriculationVehicule() : null);

        }

        return dto;
    }

    @Override
    public List<DetailADateDtoOut> listDetailAttributionDto(List<DetailCarburantA> detailCarburantA) {

        List<DetailADateDtoOut> dto = new ArrayList<>();

        for(DetailCarburantA detail: detailCarburantA){
            DetailADateDtoOut dtoss = new DetailADateDtoOut();
            dtoss = this.DetailAttributionDto(detail);
            dto.add(dtoss);
        }
        return dto;
    }

    @Override
    public List<DetailCarburantA> getListDetailCarburantAByAttribution(Attribution attribution) {
        return detailCarburantARepository.findAllByAttribution(attribution);
    }

    @Override
    public List<DetailCarburantA> getListDetailCarburantAByListAttribution(List<Attribution> attributions) {

        List<DetailCarburantA> detail = new ArrayList<>();

        for(Attribution attribution: attributions){
            List<DetailCarburantA> details = this.getListDetailCarburantAByAttribution(attribution);
            detail.addAll(details);
        }
        return detail;
    }

    @Override
    public List<DetailCarburantA> getListDetailCarburantAByListAttributionEmploye(List<Attribution> attribution, Employe employe) {
        List<DetailCarburantA> details = this.getListDetailCarburantAByListAttribution(attribution);
        List<DetailCarburantA> detail1 = new ArrayList<>();

        for(DetailCarburantA d: details){
            if(d.getEmployeDmd().getEmploye().equals(employe)) {
                detail1.add(d);
            }
        }
        return detail1;
    }

    @Override
    public List<DetailCarburantA> listDetailCarburantAByDateBetween(EtatAttributionEmployeDto dto) {
        List<Attribution> attribution = attributionRepository.findAllByDateAttributionBetween(dto.getDebut(),dto.getFin());
        Employe employe = employeRepository.getById(dto.getEmploye());

        List<DetailCarburantA> detail = new ArrayList<>();

        if(dto.getEmploye() != 0L) {
            detail = this.getListDetailCarburantAByListAttributionEmploye(attribution, employe);
        } else if(dto.getEmploye() == 0L) {
            detail = this.getListDetailCarburantAByListAttribution(attribution);
        }
        return detail;
    }

}
