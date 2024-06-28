package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.etat.DmdPapierDetailResponse;
import ci.nkagou.parcauto.dtos.etat.DmdPapierResponse;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.TypeAttribution;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.AttributionService;
import ci.nkagou.parcauto.services.EtatService;
import ci.nkagou.parcauto.utils.DateConvert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtatServiceImpl implements EtatService {
    private final AttributionService attributionService;
    private final VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository;
    private final VehiculeAttRepository vehiculeAttRepository;
    private final CarburantAttRepository carburantAttRepository;
    private final DetailVehiculeARepository detailVehiculeARepository;
    private final DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository;
    private final DetailCarburantARepository detailCarburantARepository;
    private final EmployeDmdRepository employeDmdRepository;
    public EtatServiceImpl(AttributionService attributionService, VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository, VehiculeAttRepository vehiculeAttRepository, CarburantAttRepository carburantAttRepository, DetailVehiculeARepository detailVehiculeARepository, DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository, DetailCarburantARepository detailCarburantARepository, EmployeDmdRepository employeDmdRepository) {
        this.attributionService = attributionService;
        this.vehiculeChauffeurAttRepository = vehiculeChauffeurAttRepository;
        this.vehiculeAttRepository = vehiculeAttRepository;
        this.carburantAttRepository = carburantAttRepository;
        this.detailVehiculeARepository = detailVehiculeARepository;
        this.detailVehiculeChauffeurARepository = detailVehiculeChauffeurARepository;
        this.detailCarburantARepository = detailCarburantARepository;
        this.employeDmdRepository = employeDmdRepository;
    }


    @Override
    public List<EmployeDmd> EMPLOYE_DMD_LIST(List<DetailVehiculeChauffeurA> detailVehdiculeChauffeurAS) {
        List <EmployeDmd> employeDmds=new ArrayList<>();
        for(DetailVehiculeChauffeurA detailVehiculeChauffeurA:detailVehdiculeChauffeurAS){
            EmployeDmd employeDmd =detailVehiculeChauffeurA.getEmployeDmd();
            employeDmds.add(employeDmd);
        }
        return employeDmds;

        //return detailVehdiculeChauffeurAS.stream().map(DetailVehiculeChauffeurA::getEmployeDmd).collect(Collectors.toList());


    }
    @Override
    public List<EmployeDmd> employeDmdList(List<DetailVehiculeA> detailVehiculeAS){
        List <EmployeDmd> employeDmdList=new ArrayList<>();
        for(DetailVehiculeA detailVehiculeA:detailVehiculeAS){
            EmployeDmd employeDmd=detailVehiculeA.getEmployeDmd();
            employeDmdList.add(employeDmd);
        }
        return employeDmdList;
    }


    @Override
    public List <EmployeDmd> employeDmds(List<DetailCarburantA> detailcarburantAtts){
        List <EmployeDmd> employeDmds=new ArrayList<>();
        for(DetailCarburantA carburantAtt:detailcarburantAtts){
            EmployeDmd employeDmd=carburantAtt.getEmployeDmd();
            employeDmds.add(employeDmd);
        }
        return  employeDmds;
    }


    @Override
    public List<DmdPapierDetailResponse> DMD_PAPIER_DETAIL_RESPONSE(List<EmployeDmd> employeDmdList) {
        List< DmdPapierDetailResponse> dmdPapierDetailResponseList=new ArrayList<>();
        for(EmployeDmd employeDmd:employeDmdList){
            DmdPapierDetailResponse dmdPapierDetailResponse=new DmdPapierDetailResponse();
            dmdPapierDetailResponse.setNomPrenom(employeDmd.getEmploye().toNomComplet());
            dmdPapierDetailResponse.setDestination(employeDmd.getDestination().getNomDestination());
            dmdPapierDetailResponse.setMotif(employeDmd.getMotif().getNomMotif());
            dmdPapierDetailResponseList.add(dmdPapierDetailResponse);

        }

        return dmdPapierDetailResponseList;
    }

    @Override
    public DmdPapierResponse PAPIER_RESPONSE(Long id) {
//        Attribution attribution=attributionService.findById(id);
        Attribution attribution=attributionService.findAttributionByid(id);
        //List<EmployeDmd> employeDmds=this.EMPLOYE_DMD_LIST(v);

        DmdPapierResponse papierResponse=new DmdPapierResponse();
        List<EmployeDmd> employeDmds;

        if(attribution.getTypeAttribution()== TypeAttribution.VEHICULE_CHAUFFEUR) {
            //c'est vehicule et chauffeur
            VehiculeChauffeurAtt vehiculeChauffeurAtt=vehiculeChauffeurAttRepository.getById(attribution.getIdAttribution());
            List <DetailVehiculeChauffeurA> list = detailVehiculeChauffeurARepository.findAllByAttribution(attribution);
            employeDmds=this.EMPLOYE_DMD_LIST(list);

            papierResponse.setMoyenDemande("VEHICULE + CHAUFFEUR");
            papierResponse.setNomChauffeur(vehiculeChauffeurAtt.getEmploye().toNomComplet());
            papierResponse.setImmatriculation(vehiculeChauffeurAtt.getVehicule().getImmatriculation());



//            employeDmds=detailVehiculeChauffeurARepository.findByAttribution(attribution);

            papierResponse.setDateHeureDepart(DateConvert.getStringDate(vehiculeChauffeurAtt.getDateDeDepart()));
            papierResponse.setDateHeureArrivee(DateConvert.getStringDate(vehiculeChauffeurAtt.getDateArrivee()));
           /* papierResponse.setKmDepart(Integer.toString(vehiculeChauffeurAtt.getKilometrageDebut()));
            papierResponse.setKmArrivee(Integer.toString(vehiculeChauffeurAtt.getKilometrageFin()));*/
            papierResponse.setKmDepart(String.valueOf(vehiculeChauffeurAtt.getKilometrageDebut()));
            papierResponse.setKmArrivee(String.valueOf(vehiculeChauffeurAtt.getKilometrageFin()));


        }else if(attribution.getTypeAttribution()==TypeAttribution.VEHICULE){

            //c'est vehicule
            VehiculeAtt vehiculeAtt = vehiculeAttRepository.getById(attribution.getIdAttribution());
            papierResponse.setMoyenDemande("VEHICULE");
            papierResponse.setImmatriculation(vehiculeAtt.getVehicule().getImmatriculation());
            papierResponse.setNomChauffeur("");
            List <DetailVehiculeA> list = detailVehiculeARepository.findAllByAttribution(attribution);
            employeDmds=this.employeDmdList(list);


            papierResponse.setDateHeureDepart(DateConvert.getStringDate(vehiculeAtt.getDateDeDepart()));
            papierResponse.setDateHeureArrivee(DateConvert.getStringDate(vehiculeAtt.getDateArrivee()));
            papierResponse.setKmDepart(String.valueOf(vehiculeAtt.getKilometrageDebut()));
            papierResponse.setKmArrivee(String.valueOf(vehiculeAtt.getKilometrageFin()));


        }else {
            //c'est Transport
            CarburantAtt carburantAtt = carburantAttRepository.getById(attribution.getIdAttribution());
            papierResponse.setMoyenDemande("ORIENTATION_TRANSPORT");
            papierResponse.setImmatriculation("");
            papierResponse.setNomChauffeur("");
            List <DetailCarburantA> carburantAtts = detailCarburantARepository.findAllByAttribution(attribution);
            employeDmds=this.employeDmds(carburantAtts);



            papierResponse.setDateHeureDepart(DateConvert.getStringDate(carburantAtt.getDateDeDepart()));
            papierResponse.setDateHeureArrivee(DateConvert.getStringDate(carburantAtt.getDateArrivee()));
            papierResponse.setKmDepart("");
            papierResponse.setKmArrivee("");
        }

        papierResponse.setDmdPapierResponseList(this.DMD_PAPIER_DETAIL_RESPONSE(employeDmds));

        return papierResponse;
    }
}
