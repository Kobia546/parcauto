package ci.nkagou.parcauto.services.impl;



import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.*;
import ci.nkagou.parcauto.repositories.*;
import ci.nkagou.parcauto.services.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ci.nkagou.parcauto.enums.StatutAttrib.*;
import static ci.nkagou.parcauto.enums.TypeAttribution.*;

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
    private DmdRepository dmdRepository;
    private EmployeDmdRepository employeDmdRepository;
    private final EmployeService employeService;
    private VehiculeAttRepository vehiculeAttRepository;
    private final VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository;
    private VehiculeService vehiculeService;
    private DetailVehiculeARepository detailVehiculeARepository;
    private DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository;
    private DetailCarburantARepository detailCarburantARepository;

    private CarburantAttRepository carburantAttRepository;



    public AttributionServiceImpl(VehiculeRepository vehiculeRepository, EmployeRepository employeRepository, EmployeDmdRepository employeDmdRepository, AttributionRepository attributionRepository, VehiculeHistoriqueRepository vehiculeHistoriqueRepository, ChauffeurHistoriqueRepository chauffeurHistoriqueRepository, VehiculeChauffeurAttRepository vehiculeChauffeurAttRepository, VehiculeAttRepository vehiculeAttRepository, CarburantAttRepository carburantAttRepository, DmdRepository dmdRepository, VehiculeService vehiculeService, EmployeService employeService,DetailVehiculeARepository detailVehiculeARepository,DetailVehiculeChauffeurARepository detailVehiculeChauffeurARepository,DetailCarburantARepository detailCarburantARepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeHistoriqueRepository = vehiculeHistoriqueRepository;
        this.chauffeurHistoriqueRepository = chauffeurHistoriqueRepository;
        this.employeRepository = employeRepository;
        this.employeDmdRepository = employeDmdRepository;
        this.attributionRepository = attributionRepository;
        this.vehiculeChauffeurAttRepository = vehiculeChauffeurAttRepository;
        this.vehiculeAttRepository = vehiculeAttRepository;
        this.carburantAttRepository = carburantAttRepository;
        this.dmdRepository = dmdRepository;
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
        this.detailVehiculeARepository = detailVehiculeARepository;
        this.detailVehiculeChauffeurARepository = detailVehiculeChauffeurARepository;
        this.detailCarburantARepository = detailCarburantARepository;
    }

    @Override
    public Attribution findAttributionByid(Long id) {
        return attributionRepository.getById(id);
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

    @SneakyThrows
    @Override
    public AttributionDtoOut attributionDto(Attribution attribution) {
        AttributionDtoOut dto = new AttributionDtoOut();

        dto.setId(attribution.getIdAttribution());
        dto.setTypeAttribution(attribution.getTypeAttribution().toString());
        if (attribution != null) {
            // Vérifiez si getVehiculeId() n'est pas null
            if (attribution.getVehiculeId() != null) {
                dto.setVehicule(attribution.getVehiculeId().getImmatriculation());
            } else {
                // Gérer le cas où getVehiculeId() est null
                // Par exemple, vous pouvez définir un message d'erreur ou une valeur par défaut
                dto.setVehicule("Vehicule ID is null");
            }
        } else {
            // Gérer le cas où attribution est null
            // Par exemple, vous pouvez définir un message d'erreur ou lancer une exception personnalisée
            throw new CustomException("Attribution is null");
        }
        if (attribution instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) attribution;
            dto.setMotif(vehiculeAtt.getMotif() != null ? vehiculeAtt.getMotif().toString() : "Motif is null");
        }

        if (attribution != null) {
            // Vérifiez si getEmployeDmd() n'est pas null
            if (attribution.getEmployeDmd() != null) {
                // Vérifiez si getMotif() n'est pas null
                if (attribution.getEmployeDmd().getDestination() != null) {
                    dto.setDestination(String.valueOf(attribution.getEmployeDmd().getDestination()));
                } else {
                    // Gérer le cas où getMotif() est null
                    dto.setDestination("Destination is null");
                }
            } else {
                // Gérer le cas où getEmployeDmd() est null
                dto.setDestination("Destination is null");
            }
        } else {
            // Gérer le cas où attribution est null
            throw new CustomException("Destination is null is null");
        }



        if (attribution.getKilometrageFin() != null) {
            dto.setKilometrageFin(attribution.getKilometrageFin());
        } else {
            // Assign a default value or handle the null case
            dto.setKilometrageFin(10000);
        }
        dto.setDateAttribution(attribution.getDateAttribution().toString().replace("T", " "));
        dto.setDateDeDepart(attribution.getDateDeDepart() != null ? attribution.getDateDeDepart().toString().replace("T", " ") : null);
        dto.setDateArrivee(attribution.getDateArrivee() != null ? attribution.getDateArrivee().toString().replace("T", " ") : null);
        if (attribution.getKilometrageDebut() != null) {
            dto.setKilometrageDebut(attribution.getKilometrageDebut());
        } else {
            // Assign a default value or handle the null case
            dto.setKilometrageDebut(10);
        }
        //dto.setNomComplet(attribution.getEmployeDmd() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);

        dto.setStatutAttrib(attribution.getStatutAttrib() != null ? attribution.getStatutAttrib().toString() : null);
        dto.setDuration(attribution.calculateDuration() != null ? attribution.calculateDuration(): null);


        List<DetailVehiculeA> details = attribution.getDetailVehiculeA();
        StringBuilder concatenatedNames = new StringBuilder();
        if (details != null && !details.isEmpty()) {
            for (DetailVehiculeA detail : details) {
                String name = detail.getEmployeDmd().getEmploye().toNomComplet();
                concatenatedNames.append(name).append(", ");
            }

            if (concatenatedNames.length() > 0) {
                // Remove the trailing comma and space before setting the dto's NomComplet
                String finalNames = concatenatedNames.substring(0, concatenatedNames.length() - 2);
                dto.setNomComplet(finalNames);
            }
        }

        List<DetailVehiculeChauffeurA> detailss = attribution.getDetailVehiculeChauffeurA();
        Set<String> uniqueNames = detailss.stream()
                .map(detail -> detail.getEmployeDmd().getEmploye().toNomComplet())
                .collect(Collectors.toSet());

        StringBuilder concatenatedName = new StringBuilder();
        for (String name : uniqueNames) {
            concatenatedName.append(name).append(", ");
        }

        if (concatenatedName.length() > 0) {
            // Remove the trailing comma and space before setting the dto's NomComplet
            String finalNames = concatenatedName.substring(0, concatenatedName.length() - 2);
            dto.setNomComplet(finalNames);
        }

        List<DetailCarburantA> detailz = attribution.getDetailCarburantA();
        StringBuilder concatenated = new StringBuilder();
        if (detailz != null && !detailz.isEmpty()) {
            for (DetailCarburantA detail : detailz) {
                String name = detail.getEmployeDmd().getEmploye().toNomComplet();
                concatenated.append(name).append(", ");
            }

            if (concatenated.length() > 0) {
                // Remove the trailing comma and space before setting the dto's NomComplet
                String finalNames = concatenated.substring(0, concatenated.length() - 2);
                dto.setNomComplet(finalNames);
            }
        }

        if (attribution instanceof VehiculeAtt) {
            VehiculeAtt vehiculeAtt = (VehiculeAtt) attribution;
            dto.setStatutVehiculeA(vehiculeAtt.getStatutVehiculeA() != null ? vehiculeAtt.getStatutVehiculeA().toString() : null);

            //dto.setNomComplet(attribution.getDetailVehiculeA() != null ? attribution.getEmployeDmd().getEmploye().toNomComplet() : null);
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
//            dto.setRecuCarburant(carburantAtt.getRecuCarburant() != null ?  carburantAtt.getRecuCarburant() : null);
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

    /*@Override
    public CombineAttributionDtoOut listAttributionToCombineDto(List<Attribution> attributions) {
        List<AttributionDtoOut> dtos = new ArrayList<>();
        for (Attribution attribution : attributions)
        {
            AttributionDtoOut dto = new AttributionDtoOut();

            dto = this.attributionDto(attribution);
            dtos.add(dto);
        }
        CombineAttributionDtoOut combineDto = new CombineAttributionDtoOut(dtos);
        return combineDto;
    }*/

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
    /*@Override
    public List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe) {
        return attributionRepository.findAttributionsByEmployeDmdEmploye(employe);
    }*/


    @Override
    public List<Attribution> listAttributionByDateBetween(EtatAttributionDto dto) {
        return attributionRepository.findAllByDateAttributionBetween(dto.getDebut(),dto.getFin());
    }

    @Override
    public List<Attribution> listAttributionByDateBetweens(EtatAttributionEmployeDto dto) {
        return attributionRepository.findAllByDateAttributionBetween(dto.getDebut(),dto.getFin());
    }

    @Override
    public List<Attribution> listAttributionByDateBetweenAndVehicule(EtatAttributionDto dto) {


        List<Attribution> result = new ArrayList<>();

        Vehicule vehicule = vehiculeService.findById(dto.getVehicule());

        List<Attribution> attribution1 = vehiculeAttRepository.findByDateAttributionBetweenAndVehicule(dto.getDebut(), dto.getFin(), vehicule);
        List<Attribution> attribution2 = vehiculeChauffeurAttRepository.findByDateAttributionBetweenAndVehicule(dto.getDebut(), dto.getFin(),vehicule);

        result.addAll(attribution1);
        result.addAll(attribution2);

        return result;

    }

    @Override
    public List<Attribution> listAttributionByDateBetweenAndEmploye(EtatAttributionDto dto) {
        Employe employe = employeService.findById(dto.getEmploye());
        return vehiculeChauffeurAttRepository.findByDateAttributionBetweenAndEmploye(dto.getDebut(),dto.getFin(),employe);
    }

    /*@Override
    public List<Attribution> listAttributionByDateBetweenAndEmployes(EtatAttributionEmployeDto dto) {
        List<Attribution> result = new ArrayList<>();
        Employe employe = employeService.findById(dto.getEmploye());
        List<Attribution> attribution1 = vehiculeAttRepository.findAttributionsByDateAttributionBetweenAndEmployeDmdEmploye(dto.getDebut(), dto.getFin(), employe);
        List<Attribution> attribution2 = vehiculeChauffeurAttRepository.findAttributionsByDateAttributionBetweenAndEmployeDmdEmploye(dto.getDebut(), dto.getFin(), employe);

        result.addAll(attribution1);
        result.addAll(attribution2);

        return result;
    }*/

    @Override
    public List<Attribution> listAttributionByDateBetweenAndEmployeAndVehicule(EtatAttributionDto dto) {
        Employe employe = employeService.findById(dto.getEmploye());
        Vehicule vehicule = vehiculeService.findById(dto.getVehicule());
        return vehiculeChauffeurAttRepository.findByDateAttributionBetweenAndEmployeAndVehicule(dto.getDebut(),dto.getFin(),employe,vehicule);
    }

    @Override
    public List<Attribution> listEtatAttribution(EtatAttributionDto dto) {

        List<Attribution> attribution = new ArrayList<>();



        if(dto.getEmploye() == 0L && dto.getVehicule() == 0L){
              attribution = this.listAttributionByDateBetween(dto);
        }else if(dto.getEmploye() != 0L && dto.getVehicule() == 0L){
              attribution = this.listAttributionByDateBetweenAndEmploye(dto);
        }else if(dto.getEmploye() == 0L && dto.getVehicule() != 0L){
              attribution = this.listAttributionByDateBetweenAndVehicule(dto);
        }else if(dto.getEmploye() != 0L && dto.getVehicule() != 0L){
              attribution = this.listAttributionByDateBetweenAndEmployeAndVehicule(dto);
        }

        return attribution;
    }

    /*@Override
    public List<Attribution> listEtatAttributionEmploye(EtatAttributionEmployeDto dto) {

        List<Attribution> attribution = new ArrayList<>();

        if(dto.getEmploye() == 0L ){
            attribution = this.listAttributionByDateBetweens(dto);
        }else if(dto.getEmploye() != 0L ){
            attribution = this.listAttributionByDateBetweenAndEmployes(dto);
        }

        return attribution;
    }*/



    /*@Override
    public List<VehiculeAtt> listAttributionByDateBetweenAndVehicule(EtatVehiculeDto dto) {
        return  vehiculeAttRepository.findByDateAttributionBetweenAndVehicule(dto.getDebut(),dto.getFin(),dto.getVehicule());
    }*/

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
        //vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        //vehiculeAtt.setEmploye(attributionDtoZ.getEmploye());
        vehiculeAtt.setVehicule(attributionDtoZ.getVehicule());
        vehiculeAtt.setStatutAttrib(ANNULER);
        vehiculeAtt.setObservation(attributionDtoZ.getObservation());

        return attributionRepository.save(vehiculeAtt);
    }


    @Override
    public AttributionDtoOut getAttributionDto(Long id, Class<? extends Attribution>... attributionClasses) {
        Attribution attribution = attributionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No data found for id: " + id));

        for (Class<? extends Attribution> attributionClass : attributionClasses) {
            if (attributionClass.isInstance(attribution)) {
                return this.attributionDto(attributionClass.cast(attribution));
            }
        }

        throw new IllegalArgumentException("Attribution with id " + id + " is not an instance of any of the provided classes.");
    }


    @Override
    public Attribution annulerAttributionVehiculeChauffeur(AttributionDtoZ attributionDtoZ) {
        VehiculeChauffeurAtt vehiculeChauffeurAtt = vehiculeChauffeurAttRepository.getById(attributionDtoZ.getId());

        vehiculeChauffeurAtt.setDateAttribution(attributionDtoZ.getDateAttribution());
        //vehiculeChauffeurAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
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
        //carburantAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
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
        VehiculeAtt vehiculeAtt = new VehiculeAtt();

        vehiculeAtt.setVehicule(vehicule);
        vehiculeAtt.setDateAttribution(LocalDateTime.now());
        vehiculeAtt.setStatutAttrib(EN_ATTENTE);
        vehiculeAtt.setTypeAttribution(VEHICULE);
        vehiculeAtt.setKilometrageDebut(dto.getKilometrageDebut());

        VehiculeAtt vehiculeAtt1 = attributionRepository.save(vehiculeAtt);

        List<DetailVehiculeADto> dtos = dto.getDetailVehiculeADto();
        for (DetailVehiculeADto dtoss  : dtos){
            EmployeDmd employeDmd = employeDmdRepository.getById(dtoss.getIdEmployeDmd());
            employeDmd.setStatut(Statut.ATTRIBUTION);
            EmployeDmd employeDmd1 = employeDmdRepository.save(employeDmd);

            DetailVehiculeA detail = new DetailVehiculeA();

            detail.setAttribution(vehiculeAtt1);
            detail.setEmployeDmd(employeDmd1);

            detailVehiculeARepository.save(detail);

           /* EmployeDmd employeDmd = employeDmdRepository.getById(id);
            //VehiculeAtt vehiculeAtt = new VehiculeAtt();
            vehiculeAtt.setDateAttribution(LocalDateTime.now());
            vehiculeAtt.setVehicule(vehicule);

            //vehiculeAtt.setEmployeDmd(employeDmd);
            attributionRepository.save(vehiculeAtt);
            employeDmds.add(employeDmd);
            employeDmd.setStatut(Statut.ATTRIBUTION);
            employeDmdRepository.save(employeDmd);*/

        }
        //return attributionRepository.save(attribution);
    }

    @Override
    public void createVehiculeChauffeur(AttributionVehiculeChauffeurAttDto dtos) {

        Vehicule vehicule = vehiculeRepository.getById(dtos.getVehicule());
        vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        Employe chauffeur = employeRepository.getById(dtos.getEmploye());

        chauffeur.setStatutChauffeur(StatutChauffeur.INDISPONIBLE);
        Employe chauffeur1 = employeRepository.save(chauffeur);
        VehiculeChauffeurAtt att = new VehiculeChauffeurAtt();

        att.setVehicule(vehicule1);
        att.setEmploye(chauffeur1);
        att.setKilometrageDebut(dtos.getKilometrageDebut());
        att.setKilometrageFin(dtos.getKilometrageFin());

        att.setDateAttribution(LocalDateTime.now());
        att.setStatutAttrib(EN_ATTENTE);
        att.setTypeAttribution(VEHICULE_CHAUFFEUR);

        att = vehiculeChauffeurAttRepository.save(att);

        List<DetailVehiculeChauffeurADto> list = dtos.getDetailVehiculeChauffeurADto();

        for (DetailVehiculeChauffeurADto detail : list) {
            EmployeDmd employeDmd = employeDmdRepository.getById(detail.getIdEmployeDmd());

            employeDmd.setStatut(Statut.ATTRIBUTION);
            EmployeDmd employeDmd1 = employeDmdRepository.save(employeDmd);

            DetailVehiculeChauffeurA d = new DetailVehiculeChauffeurA();
            d.setEmployeDmd(employeDmd1);
            d.setAttribution(att);
            detailVehiculeChauffeurARepository.save(d);

        }


/*      Vehicule vehicule = vehiculeRepository.getById(dtos.getVehicule());
        //vehicule.setStatutVehicule(StatutVehicule.EN_COURSE);
        //Vehicule vehicule1 = vehiculeRepository.save(vehicule);
        Employe chauffeur = employeRepository.getById(dtos.getEmploye());

        VehiculeChauffeurAtt vehiculeChauffeurAtt = new VehiculeChauffeurAtt();

        vehiculeChauffeurAtt.setVehicule(vehicule);
        vehiculeChauffeurAtt.setEmploye(chauffeur);
        vehiculeChauffeurAtt.setDateAttribution(LocalDateTime.now());
        vehiculeChauffeurAtt.setStatutAttrib(EN_ATTENTE);
        vehiculeChauffeurAtt.setTypeAttribution(VEHICULE);

        VehiculeChauffeurAtt vehiculeChauffeurAtt1 = attributionRepository.save(vehiculeChauffeurAtt);


        List<DetailVehiculeChauffeurADto> dto = dtos.getDetailVehiculeChauffeurADto();
        for (DetailVehiculeChauffeurADto dtoss : dto){
            EmployeDmd employeDmd = employeDmdRepository.getById(dtoss.getId());
            DetailVehiculeChauffeurA detail = new DetailVehiculeChauffeurA();
            detail.setAttribution(vehiculeChauffeurAtt1);
            detail.setEmployeDmd(employeDmd);

            detailVehiculeChauffeurARepository.save(detail);*/

        //return attributionRepository.save(attribution);
    }
    @Override
    public Attribution createCarburant(AttributionCarburantAttDto dtos) {

        CarburantAtt carburantAtt = new CarburantAtt();

        carburantAtt.setDateAttribution(LocalDateTime.now());
        carburantAtt.setStatutAttrib(EN_ATTENTE);
        carburantAtt.setTypeAttribution(ORIENTATION_TRANSPORT);

        CarburantAtt carburantAtt1 = attributionRepository.save(carburantAtt);
        List<DetailCarburantA> d = new ArrayList<>();

        List<DetailCarburantADto> dto = dtos.getDetailCarburantADto();
        for (DetailCarburantADto dtoss : dto){
            EmployeDmd employeDmd = employeDmdRepository.getById(dtoss.getIdEmployeDmd());

            employeDmd.setStatut(Statut.ATTRIBUTION);
            EmployeDmd employeDmd1 = employeDmdRepository.save(employeDmd);

            DetailCarburantA detail = new DetailCarburantA();
            detail.setAttribution(carburantAtt1);
            detail.setEmployeDmd(employeDmd1);

            DetailCarburantA detailCarburantA = detailCarburantARepository.save(detail);

            d.add(detailCarburantA);

        }
        carburantAtt1.setDetailCarburantA(d);
        return carburantAtt1;
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
        //vehiculeChauffeurAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeChauffeurAtt.setDateArrivee(attributionDtoZ.getDateArrivee());
        vehiculeChauffeurAtt.setKilometrageFin(attributionDtoZ.getKilometrageFin());

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
        //vehiculeChauffeurAtt.setMotif(attributionDtoZ.getMotif());
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
        //vehiculeChauffeurAtt1.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeChauffeurAtt1.setDetailVehiculeChauffeurA(attributionDtoZ.getDetailVehiculeChauffeurA());
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
        //vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
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
        //vehiculeAtt.setEmployeDmd(attributionDtoZ.getEmployeDmd());
        vehiculeAtt.setDateArrivee(attributionDtoZ.getDateArrivee());
        vehiculeAtt.setKilometrageFin(attributionDtoZ.getKilometrageFin());

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
        //vehiculeAtt.setMotif(attributionDtoZ.getMotif());
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

    @Override
    public List<VehiculeAtt> listAttributionByDateBetween(EtatChauffeurDto dto) {
        return vehiculeAttRepository.findAllByDateAttributionBetween(dto.getDebut(), dto.getFin());
    }



    /*@Override
    public void delete(Attribution attribution) {
        attributionRepository.delete(attribution);
    }*/




}
