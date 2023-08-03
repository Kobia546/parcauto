package ci.nkagou.parcauto.services.impl;



import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.AttributionService;
import ci.nkagou.parcauto.services.EmployeService;
import ci.nkagou.parcauto.services.VehiculeHistoriqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ci.nkagou.parcauto.enums.StatutAttrib.*;

@Service
@Transactional
@Slf4j

public class AttributionServiceImpl implements AttributionService {


    private AttributionRepository attributionRepository;

    private  VehiculeRepository vehiculeRepository;
    private  VehiculeHistoriqueRepository vehiculeHistoriqueRepository;
    private  VehiculeHistoriqueService vehiculeHistoriqueService;
    private ChauffeurHistoriqueRepository chauffeurHistoriqueRepository;
    private EmployeRepository employeRepository;
    private EmployeDmdRepository employeDmdRepository;
    private EmployeService employeService;
    private VehiculeAttRepository vehiculeAttRepository;
    private final VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository;

    private CarburantAttRepository carburantAttRepository;



    public AttributionServiceImpl(VehiculeRepository vehiculeRepository, EmployeRepository employeRepository, EmployeDmdRepository employeDmdRepository, AttributionRepository attributionRepository, VehiculeHistoriqueRepository vehiculeHistoriqueRepository, ChauffeurHistoriqueRepository chauffeurHistoriqueRepository, VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository,VehiculeAttRepository vehiculeAttRepository,CarburantAttRepository carburantAttRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeHistoriqueRepository = vehiculeHistoriqueRepository;
        this.chauffeurHistoriqueRepository = chauffeurHistoriqueRepository;
        this.employeRepository = employeRepository;
        this.employeDmdRepository = employeDmdRepository;
        this.attributionRepository = attributionRepository;
        this.vehiculeChauffeurAttRepository = vehiculeChauffeurAttRepository;
        this.vehiculeAttRepository = vehiculeAttRepository;
        this.carburantAttRepository = carburantAttRepository;
    }

    @Override
    public List<Attribution> all() {
        return attributionRepository.findAll();
    }

    @Override
    public List<CarburantAtt> allAttribution() {
        return carburantAttRepository.findAll();
    }

    @Override
    public List<VehiculeAtt> allAttributionVehicule() {
        return vehiculeAttRepository.findAll();
    }

    /*@Override
    public List<VehiculeAtt> AttributionVehiculeByStatutAttrib(StatutAttrib statutAttrib1,StatutAttrib statutAttrib) {
        return vehiculeChauffeurAttRepository.AttributionVehiculeByStatutAttrib(statutAttrib1,statutAttrib);
    }*/

    @Override
    public List<VehiculeChauffeurAtt> allAttributionVehiculeChauffeur() {
        return vehiculeChauffeurAttRepository.findAll();
    }

    /*@Override
    public List<VehiculeChauffeurAtt> AttributionVehiculeChauffeurByStatutChauffeur(StatutAttrib statutAttrib1,StatutAttrib statutAttrib) {
        return vehiculeChauffeurAttRepository.AttributionVehiculeChauffeurByStatutAttrib(statutAttrib1,statutAttrib);
    }*/

    @Override
    public VehiculeChauffeurAtt findById(Long id) {
        return vehiculeChauffeurAttRepository.getById(id);
    }

    @Override
    public VehiculeAtt findVEById(Long id) {
        return vehiculeAttRepository.getById(id);
    }

    @Override
    public CarburantAtt findCCById(Long id) {
        return carburantAttRepository.getById(id);
    }

    @Override
    public Attribution getById(Long id) {
        return attributionRepository.getById(id);
    }

    @Override
    public AttributionDtoOut attributionDto(Attribution attribution) {
        AttributionDtoOut dto = new AttributionDtoOut();

        dto.setId(attribution.getIdAttribution());
        dto.setTypeAttribution(attribution.getTypeAttribution().toString());
        dto.setDateAttribution(attribution.getDateAttribution().toString());
        dto.setDateDeDepart(attribution.getDateDeDepart() != null ? attribution.getDateDeDepart().toString() : null);
        dto.setDateArrivee(attribution.getDateArrivee() != null ? attribution.getDateArrivee().toString() : null);
        dto.setNomComplet(attribution.getEmployeDmd() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);
        dto.setStatutAttrib(attribution.getStatutAttrib() != null ? attribution.getStatutAttrib().toString() : null);

        if (attribution instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) attribution;
            dto.setStatutVehiculeA(vehiculeAtt.getStatutVehiculeA() != null ? vehiculeAtt.getStatutVehiculeA().toString() : null);
            dto.setMotif(vehiculeAtt.getMotif() != null ? vehiculeAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeAtt.getObservation() != null ? vehiculeAtt.getObservation() : null);
            dto.setVehicule(vehiculeAtt.getVehicule().getImmatriculation() != null ? vehiculeAtt.getVehicule().getImmatriculation() : null);

        }

        if (attribution instanceof VehiculeChauffeurAtt) {
            VehiculeChauffeurAtt vehiculeChauffeurAtt = (VehiculeChauffeurAtt) attribution;
            dto.setStatutVehiculeA(vehiculeChauffeurAtt.getStatutVehiculeA() != null ? vehiculeChauffeurAtt.getStatutVehiculeA().toString() : null);
            dto.setMotif(vehiculeChauffeurAtt.getMotif() != null ? vehiculeChauffeurAtt.getMotif().toString() : null);
            dto.setObservation(vehiculeChauffeurAtt.getObservation() != null ? vehiculeChauffeurAtt.getObservation() : null);
            dto.setVehicule(vehiculeChauffeurAtt.getVehicule().getImmatriculation() != null ? vehiculeChauffeurAtt.getVehicule().getImmatriculation() : null);
            dto.setEmploye(vehiculeChauffeurAtt.getEmploye().toNomComplet() != null ? vehiculeChauffeurAtt.getEmploye().toNomComplet() : null);
            dto.setStatutChauffeurA(vehiculeChauffeurAtt.getStatutChauffeurA() != null ? vehiculeChauffeurAtt.getStatutChauffeurA().toString() : null);
            dto.setMotifChauffeur(vehiculeChauffeurAtt.getMotifChauffeur() != null ? vehiculeChauffeurAtt.getMotifChauffeur().toString() : null);
            dto.setObservationChauffeur(vehiculeChauffeurAtt.getObservationChauffeur() != null ? vehiculeChauffeurAtt.getObservationChauffeur() : null);
        }

        if (attribution instanceof CarburantAtt) {
            CarburantAtt carburantAtt = (CarburantAtt) attribution;
            dto.setRecuCarburant(carburantAtt.getRecuCarburant() != null ?  carburantAtt.getRecuCarburant() : null);
            dto.setMontant(carburantAtt.getMontant() != 0 ? carburantAtt.getMontant() : 0);
            dto.setLitre(carburantAtt.getLitre() != 0 ? carburantAtt.getLitre() : 0);
            dto.setVehicule(carburantAtt.getImmatriculationVehicule() != null ? carburantAtt.getImmatriculationVehicule() : null);

        }

        return dto;
    }

    @Override
    public List<AttributionDtoOut> listAttributionToDto(List<Attribution> attributions) {

        List<AttributionDtoOut> dtos = new ArrayList<>();
        for (Attribution attribution : attributions)
        {
            AttributionDtoOut dto = new AttributionDtoOut();

            dto = this.attributionDto(attribution);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public List<AttributionDtoOut> listAttributionToDtoCarburant(List<CarburantAtt> carburantAtts) {
        List<AttributionDtoOut> dtos = new ArrayList<>();
        for (CarburantAtt carburantAtt : carburantAtts)
        {
            AttributionDtoOut dto = new AttributionDtoOut();

            dto = this.attributionDto(carburantAtt);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<AttributionDtoOut> listAttributionToDtoVehicule(List<VehiculeAtt> vehiculeAtts) {
        List<AttributionDtoOut> dtos = new ArrayList<>();
        for (VehiculeAtt vehiculeAtt: vehiculeAtts)
        {
            AttributionDtoOut dto = new AttributionDtoOut();

            dto = this.attributionDto(vehiculeAtt);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AttributionDtoOut> listAttributionToDtoVehiculeChauffeur(List<VehiculeAtt> vehiculeAtts, List<VehiculeChauffeurAtt> vehiculeChauffeurAtts) {
        List<AttributionDtoOut> dtos = new ArrayList<>();

        // Loop through VehiculeAtt list
        for (VehiculeAtt vehiculeAtt : vehiculeAtts) {
            AttributionDtoOut dto = this.attributionDto(vehiculeAtt);
            dtos.add(dto);
        }

        // Loop through VehiculeChauffeurAtt list
        for (VehiculeChauffeurAtt vehiculeChauffeurAtt : vehiculeChauffeurAtts) {
            AttributionDtoOut dto = this.attributionDto(vehiculeChauffeurAtt);
            dtos.add(dto);
        }

        return dtos;
    }

    //FONTION RETOUR ATTRIBUTION RETOUR EMPLOYER DMD ET EMPLOYE
    @Override
    public List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe) {
        return attributionRepository.findAttributionsByEmployeDmdEmploye(employe);
    }

    /*@Override
    public Attribution commencer(Long id,Attribution attribution) {

        Attribution attribution1 = attributionRepository.getById(id);

        attribution1.setDateDeDepart(LocalDateTime.now());
        attribution1.setStatutAttrib(EN_COURSE);


        return attributionRepository.save(attribution1);
    }*/

    @Override
    public Attribution annulerAttributionVehicule(AttributionDtoZ attributionDtoZ) {

        VehiculeAtt vehiculeAtt = vehiculeAttRepository.getById(attributionDtoZ.getId());

        vehiculeAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        //vehiculeAtt.setEmploye(attributionDtoZ.getEmploye());
        vehiculeAtt.setVehicule(attributionDtoZ.getVehicule());
        vehiculeAtt.setStatutAttrib(ANNULER);
        vehiculeAtt.setObservation(attributionDtoZ.getObservation());

        return attributionRepository.save(vehiculeAtt);
    }

    @Override
    public Attribution annulerAttributionVehiculeChauffeur(AttributionDtoZ attributionDtoZ) {
        VehiculeChauffeurAtt vehiculeChauffeurAtt = vehiculeChauffeurAttRepository.getById(attributionDtoZ.getId());

        vehiculeChauffeurAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        vehiculeChauffeurAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        //vehiculeAtt.setEmploye(attributionDtoZ.getEmploye());
        vehiculeChauffeurAtt.setVehicule(attributionDtoZ.getVehicule());
        vehiculeChauffeurAtt.setStatutAttrib(ANNULER);
        vehiculeChauffeurAtt.setObservation(attributionDtoZ.getObservation());

        return attributionRepository.save(vehiculeChauffeurAtt);
    }

    @Override
    public Attribution annulerAttributionCarburant(AttributionDtoZ attributionDtoZ) {
        CarburantAtt carburantAtt = carburantAttRepository.getById(attributionDtoZ.getId());

        carburantAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        carburantAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        //vehiculeAtt.setEmploye(attributionDtoZ.getEmploye());
        //vehiculeChauffeurAtt.setVehicule(attributionDtoZ.getVehicule());
        carburantAtt.setStatutAttrib(ANNULER);
        //carburantAtt.setObservation(attributionDtoZ.getObservation());

        return attributionRepository.save(carburantAtt);
    }
    /*@Override
    public AttributionDtoOut vehiculeAttDto(VehiculeAtt vehiculeAtt) {

        AttributionDtoOut dto = new AttributionDtoOut();

        dto.setDateAttribution(vehiculeAtt.getDateAttribution().toString());


        return null;
    }*/

    @Override
    public void create(AttributionDto dto) {

        Vehicule vehicule = vehiculeRepository.getById(dto.getVehicule());
        //vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        //Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        //Employe chauffeur = employeRepository.getById(dto.getEmploye());


        List<EmployeDmd> employeDmds = new ArrayList<>();
        for (Long id : dto.getDmdUserDto()){
            EmployeDmd employeDmd = employeDmdRepository.getById(id);
            VehiculeAtt vehiculeAtt = new VehiculeAtt();
            vehiculeAtt.setDateAttribution(LocalDateTime.now());
            vehiculeAtt.setVehicule(vehicule);
            vehiculeAtt.setStatutAttrib(EN_ATTENTE);
            vehiculeAtt.setTypeAttribution(TypeAttribution.VEHICULE);
            vehiculeAtt.setEmployeDmd(employeDmd);
            attributionRepository.save(vehiculeAtt);
            employeDmds.add(employeDmd);
            employeDmd.setStatut(Statut.ATTRIBUTION);
            employeDmdRepository.save(employeDmd);

        }
        //return attributionRepository.save(attribution);
    }

    @Override
    public void createVehiculeChauffeur(AttributionVehiculeChauffeurAttDto dtos) {
        Vehicule vehicule = vehiculeRepository.getById(dtos.getVehicule());
        //vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        //Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        Employe chauffeur = employeRepository.getById(dtos.getEmploye());


        List<EmployeDmd> employeDmds = new ArrayList<>();
        for (Long id : dtos.getDmdUserDto()){
            EmployeDmd employeDmd = employeDmdRepository.getById(id);
            VehiculeChauffeurAtt vehiculeChauffeurAtt = new VehiculeChauffeurAtt();
            vehiculeChauffeurAtt.setDateAttribution(LocalDateTime.now());
            vehiculeChauffeurAtt.setVehicule(vehicule);
            vehiculeChauffeurAtt.setEmploye(chauffeur);
            vehiculeChauffeurAtt.setStatutAttrib(EN_ATTENTE);
            vehiculeChauffeurAtt.setTypeAttribution(TypeAttribution.VEHICULE_CHAUFFEUR);
            vehiculeChauffeurAtt.setEmployeDmd(employeDmd);
            attributionRepository.save(vehiculeChauffeurAtt);
            employeDmds.add(employeDmd);
            employeDmd.setStatut(Statut.ATTRIBUTION);
            employeDmdRepository.save(employeDmd);

        }
        //return attributionRepository.save(attribution);
    }

    @Override
    public void createCarburant(AttributionCarburantAttDto dtos) {


        List<EmployeDmd> employeDmds = new ArrayList<>();
        for (Long id : dtos.getDmdUserDto()){
            EmployeDmd employeDmd = employeDmdRepository.getById(id);
            CarburantAtt carburantAtt = new CarburantAtt();
            carburantAtt.setDateAttribution(LocalDateTime.now());
            carburantAtt.setStatutAttrib(EN_COURSE);
            carburantAtt.setMontant(dtos.getMontant());
            carburantAtt.setDateDeDepart(dtos.getDateDeDepart());
            carburantAtt.setTypeAttribution(TypeAttribution.CARBURANT);
            carburantAtt.setEmployeDmd(employeDmd);
            attributionRepository.save(carburantAtt);
            employeDmds.add(employeDmd);
            employeDmd.setStatut(Statut.ATTRIBUTION);
            employeDmdRepository.save(employeDmd);

        }
        //return attributionRepository.save(attribution);
    }



    @Override
    public Attribution updateTerminerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ) {

        Vehicule vehicule = vehiculeRepository.getById(attributionDtoZ.getVehicule().getIdVehicule());
        vehicule.setImmatriculation(attributionDtoZ.getVehicule().getImmatriculation());
        //vehicule.setStatutVehicule(attributionDtoZ.getVehicule().getStatutVehicule());
        //Vehicule vehicule1 = vehiculeRepository.save(vehicule);

        Employe employe = employeRepository.getById(attributionDtoZ.getEmploye().getIdEmploye());

        /*VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();
        //vehiculeHistorique.setId(attributionDtoZ.getIdAttribution());
        vehiculeHistorique.setStatutHistorique(StatutHistorique.TERMINEE);
        vehiculeHistorique.setDateParcours(LocalDateTime.now());*/

        ChauffeurHistorique chauffeurHistorique = new ChauffeurHistorique();
        chauffeurHistorique.setStatutHistorique(StatutHistorique.TERMINEE);
        chauffeurHistorique.setDateParcours(LocalDateTime.now());


        VehiculeChauffeurAtt vehiculeChauffeurAtt = vehiculeChauffeurAttRepository.getById(attributionDtoZ.getId());

        vehiculeChauffeurAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        vehiculeChauffeurAtt.setEmploye(attributionDtoZ.getEmploye());
        vehiculeChauffeurAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeChauffeurAtt.setDateArrivee(attributionDtoZ.getDateArrivee());

        if (attributionDtoZ.getStatutVehiculeA().equals(StatutVehiculeA.DISPONIBLE)) {
            vehicule.setStatutVehicule(StatutVehicule.DISPONIBLE);
        } else if (attributionDtoZ.getStatutVehiculeA().equals(StatutVehiculeA.INDISPONIBLE)) {
            vehicule.setStatutVehicule(StatutVehicule.INDISPONIBLE);
        }

        if (attributionDtoZ.getStatutChauffeurA().equals(StatutChauffeurA.DISPONIBLE)) {
            employe.setStatutChauffeur(StatutChauffeur.DISPONIBLE);
        } else if (attributionDtoZ.getStatutChauffeurA().equals(StatutChauffeurA.INDISPONIBLE)) {
            employe.setStatutChauffeur(StatutChauffeur.INDISPONIBLE);
        }

        Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        Employe employe1 = employeRepository.save(employe);
        vehiculeChauffeurAtt.setStatutVehiculeA(attributionDtoZ.getStatutVehiculeA());
        vehiculeChauffeurAtt.setStatutChauffeurA(attributionDtoZ.getStatutChauffeurA());
        vehiculeChauffeurAtt.setVehicule(vehicule1);
        vehiculeChauffeurAtt.setEmploye(employe1);
        vehiculeChauffeurAtt.setMotif(attributionDtoZ.getMotif());
        vehiculeChauffeurAtt.setMotifChauffeur(attributionDtoZ.getMotifChauffeur());
        vehiculeChauffeurAtt.setStatutAttrib(TERMINEE);
        vehiculeChauffeurAtt.setObservation(attributionDtoZ.getObservation());
        vehiculeChauffeurAtt.setObservationChauffeur(attributionDtoZ.getObservationChauffeur());
        attributionRepository.save(vehiculeChauffeurAtt);

        //vehiculeHistorique.setAttribution(vehiculeChauffeurAtt);
        chauffeurHistorique.setVehiculeChauffeurAtt(vehiculeChauffeurAtt);
        //VehiculeHistorique vehiculeHistorique2 = vehiculeHistoriqueRepository.save(vehiculeHistorique);
        ChauffeurHistorique chauffeurHistorique2 = chauffeurHistoriqueRepository.save(chauffeurHistorique);

        /*List<VehiculeHistorique> vehiculeHistoriques = new ArrayList<>();
        vehiculeHistoriques.add(vehiculeHistorique2);
        vehiculeChauffeurAtt.setVehiculeHistorique(vehiculeHistoriques);*/

        List<ChauffeurHistorique> chauffeurHistoriques = new ArrayList<>();
        chauffeurHistoriques.add(chauffeurHistorique2);
        vehiculeChauffeurAtt.setChauffeurHistorique(chauffeurHistoriques);

        return vehiculeChauffeurAtt;
    }



    @Override
    public Attribution updateCommencerLaCourseVehiculeChauffeur(AttributionDtoZ attributionDtoZ) {

        Vehicule vehicule = vehiculeRepository.getById(attributionDtoZ.getVehicule().getIdVehicule());
        vehicule.setImmatriculation(attributionDtoZ.getVehicule().getImmatriculation());
        vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        Vehicule vehicule1 = vehiculeRepository.save(vehicule);

        Employe employe = employeRepository.getById(attributionDtoZ.getEmploye().getIdEmploye());
        employe.setStatutChauffeur(StatutChauffeur.EN_COURSE);
        Employe employe1 = employeRepository.save(employe);

        /*VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();
        vehiculeHistorique.setId(attributionDtoZ.getIdAttribution());
        vehiculeHistorique.setStatutHistorique(StatutHistorique.EN_COURSE);
        vehiculeHistorique.setDateParcours(LocalDateTime.now());*/

        ChauffeurHistorique chauffeurHistorique = new ChauffeurHistorique();
        chauffeurHistorique.setStatutHistorique(StatutHistorique.EN_COURSE);
        chauffeurHistorique.setDateParcours(LocalDateTime.now());

        VehiculeChauffeurAtt vehiculeChauffeurAtt1 = vehiculeChauffeurAttRepository.getById(attributionDtoZ.getId());

        vehiculeChauffeurAtt1.setDateAttribution(attributionDtoZ.getDateAttribution());
        vehiculeChauffeurAtt1.setVehicule(vehicule1);
        vehiculeChauffeurAtt1.setEmploye(employe1);
        vehiculeChauffeurAtt1.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeChauffeurAtt1.setDateDeDepart(attributionDtoZ.getDateDeDepart());
        vehiculeChauffeurAtt1.setStatutAttrib(EN_COURSE);

        attributionRepository.save(vehiculeChauffeurAtt1);

        //vehiculeHistorique.setVehiculeAtt(vehiculeChauffeurAtt1);
        chauffeurHistorique.setVehiculeChauffeurAtt(vehiculeChauffeurAtt1);
        ChauffeurHistorique chauffeurHistorique1 = chauffeurHistoriqueRepository.save(chauffeurHistorique);

        List<ChauffeurHistorique> chauffeurHistoriques = new ArrayList<>();
        chauffeurHistoriques.add(chauffeurHistorique1);
        vehiculeChauffeurAtt1.setChauffeurHistorique(chauffeurHistoriques);


        return vehiculeChauffeurAtt1;

    }

    @Override
    public Attribution updateCommencerLaCourseVehicule(AttributionDtoZ attributionDtoZ) {

        Vehicule vehicule = vehiculeRepository.getById(attributionDtoZ.getVehicule().getIdVehicule());
        vehicule.setImmatriculation(attributionDtoZ.getVehicule().getImmatriculation());
        vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        Vehicule vehicule1 = vehiculeRepository.save(vehicule);

        /*Employe employe = employeRepository.getById(attributionDtoZ.getEmploye().getIdEmploye());
        employe.setStatutChauffeur(StatutChauffeur.EN_COURSE);
        Employe employe1 = employeRepository.save(employe);*/

        VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();
        //vehiculeHistorique.setId(attributionDtoZ.getId());
        vehiculeHistorique.setStatutHistorique(StatutHistorique.EN_COURSE);
        vehiculeHistorique.setDateParcours(LocalDateTime.now());

        /*ChauffeurHistorique chauffeurHistorique = new ChauffeurHistorique();
        chauffeurHistorique.setStatutHistorique(StatutHistorique.EN_COURSE);
        chauffeurHistorique.setDateParcours(LocalDateTime.now());*/

        VehiculeAtt vehiculeAtt = vehiculeAttRepository.getById(attributionDtoZ.getId());

        vehiculeAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        vehiculeAtt.setVehicule(vehicule1);
        //vehiculeChauffeurAtt1.setEmploye(employe1);
        vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeAtt.setDateDeDepart(attributionDtoZ.getDateDeDepart());
        vehiculeAtt.setStatutAttrib(EN_COURSE);

        attributionRepository.save(vehiculeAtt);

        vehiculeHistorique.setVehiculeAtt(vehiculeAtt);
        VehiculeHistorique vehiculeHistorique1 = vehiculeHistoriqueRepository.save(vehiculeHistorique);

        List<VehiculeHistorique> vehiculeHistoriques = new ArrayList<>();
        vehiculeHistoriques.add(vehiculeHistorique1);
        vehiculeAtt.setVehiculeHistorique(vehiculeHistoriques);


        return vehiculeAtt;
    }

    @Override
    public Attribution updateTerminerLaCourseVehicule(AttributionDtoZ attributionDtoZ) {

        Vehicule vehicule = vehiculeRepository.getById(attributionDtoZ.getVehicule().getIdVehicule());
        vehicule.setImmatriculation(attributionDtoZ.getVehicule().getImmatriculation());
        //vehicule.setStatutVehicule(attributionDtoZ.getVehicule().getStatutVehicule());
        //Vehicule vehicule1 = vehiculeRepository.save(vehicule);

        //Employe employe = employeRepository.getById(attributionDtoZ.getEmploye().getIdEmploye());

        VehiculeHistorique vehiculeHistorique = new VehiculeHistorique();
        //vehiculeHistorique.setId(attributionDtoZ.getIdAttribution());
        vehiculeHistorique.setStatutHistorique(StatutHistorique.TERMINEE);
        vehiculeHistorique.setDateParcours(LocalDateTime.now());

        /*ChauffeurHistorique chauffeurHistorique = new ChauffeurHistorique();
        chauffeurHistorique.setStatutHistorique(StatutHistorique.TERMINEE);
        chauffeurHistorique.setDateParcours(LocalDateTime.now());*/


        VehiculeAtt vehiculeAtt = vehiculeAttRepository.getById(attributionDtoZ.getId());

        vehiculeAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        //vehiculeAtt.setEmploye(attributionDtoZ.getEmploye());
        vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeAtt.setDateArrivee(attributionDtoZ.getDateArrivee());

        if (attributionDtoZ.getStatutVehiculeA().equals(StatutVehiculeA.DISPONIBLE)) {
            vehicule.setStatutVehicule(StatutVehicule.DISPONIBLE);
        } else if (attributionDtoZ.getStatutVehiculeA().equals(StatutVehiculeA.INDISPONIBLE)) {
            vehicule.setStatutVehicule(StatutVehicule.INDISPONIBLE);
        }

       /* if (attributionDtoZ.getStatutChauffeurA().equals(StatutChauffeurA.DISPONIBLE)) {
            employe.setStatutChauffeur(StatutChauffeur.DISPONIBLE);
        } else if (attributionDtoZ.getStatutChauffeurA().equals(StatutChauffeurA.INDISPONIBLE)) {
            employe.setStatutChauffeur(StatutChauffeur.INDISPONIBLE);
        }*/

        Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        //Employe employe1 = employeRepository.save(employe);
        vehiculeAtt.setStatutVehiculeA(attributionDtoZ.getStatutVehiculeA());
        //vehiculeAtt.setStatutChauffeurA(attributionDtoZ.getStatutChauffeurA());
        vehiculeAtt.setVehicule(vehicule1);
        //vehiculeChauffeurAtt.setEmploye(employe1);
        vehiculeAtt.setMotif(attributionDtoZ.getMotif());
        //vehiculeAtt.setMotifChauffeur(attributionDtoZ.getMotifChauffeur());
        vehiculeAtt.setStatutAttrib(TERMINEE);
        vehiculeAtt.setObservation(attributionDtoZ.getObservation());
        //vehiculeAtt.setObservationChauffeur(attributionDtoZ.getObservationChauffeur());
        attributionRepository.save(vehiculeAtt);

        vehiculeHistorique.setVehiculeAtt(vehiculeAtt);
        //chauffeurHistorique.setVehiculeChauffeurAtt(vehiculeChauffeurAtt);
        VehiculeHistorique vehiculeHistorique2 = vehiculeHistoriqueRepository.save(vehiculeHistorique);
        //ChauffeurHistorique chauffeurHistorique2 = chauffeurHistoriqueRepository.save(chauffeurHistorique);

        List<VehiculeHistorique> vehiculeHistoriques = new ArrayList<>();
        vehiculeHistoriques.add(vehiculeHistorique2);
        vehiculeAtt.setVehiculeHistorique(vehiculeHistoriques);

        /*List<ChauffeurHistorique> chauffeurHistoriques = new ArrayList<>();
        chauffeurHistoriques.add(chauffeurHistorique2);
        vehiculeChauffeurAtt.setChauffeurHistorique(chauffeurHistoriques);*/

        return vehiculeAtt;
    }

    @Override
    public Attribution updateTerminerLaCourseCarburant(AttributionDtoZ attributionDtoZ) {

        CarburantAtt carburantAtt = carburantAttRepository.getById(attributionDtoZ.getId());

        carburantAtt.setTypeAttribution(attributionDtoZ.getTypeAttribution());
        carburantAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        carburantAtt.setDateDeDepart(attributionDtoZ.getDateDeDepart());
        carburantAtt.setDateArrivee(attributionDtoZ.getDateArrivee());
        carburantAtt.setStatutAttrib(TERMINEE);

        //carburantAtt.setRecuCarburant(attributionDtoZ.getRecuCarburant());
        carburantAtt.setImmatriculationVehicule(attributionDtoZ.getImmatriculationVehicule());
        carburantAtt.setLitre(attributionDtoZ.getLitre());
        carburantAtt.setMontant(attributionDtoZ.getMontant());


        return attributionRepository.save(carburantAtt);

    }



    /*@Override
    public void delete(Attribution attribution) {
        attributionRepository.delete(attribution);
    }*/




}
