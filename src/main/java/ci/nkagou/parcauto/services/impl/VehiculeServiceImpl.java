package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoZ;
import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.VehiculeHistoriqueRepository;
import ci.nkagou.parcauto.repositories.VehiculeRepository;
import ci.nkagou.parcauto.services.MarqueService;
import ci.nkagou.parcauto.services.TypevehiculeService;
import ci.nkagou.parcauto.services.VehiculeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeRepository vehiculeRepository;
    private VehiculeHistoriqueRepository vehiculeHistoriqueRepository;
    private TypevehiculeService typevehiculeService;
    private MarqueService marqueService;
     private AttributionRepository attributionRepository;

    @Override
    public Vehicule dtoToVehicule(VehiculeDtoZ dto) {

        Vehicule vehicule = new Vehicule();

        //Typevehicule typevehicule = typevehiculeService.findById(dto.getTypevehicule());
        //Marque marque = marqueService.findById(dto.getMarque());
        //Couleur couleur = Couleur.valueOf(dto.getCouleur());

        vehicule.setIdVehicule(dto.getIdVehicule());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setCouleur(dto.getCouleur());
        vehicule.setStatutVehicule(StatutVehicule.DISPONIBLE);
        vehicule.setDateAchat(dto.getDateAchat());
        vehicule.setNumeroChassis(dto.getNumeroChassis());
        vehicule.setCarteGrise(dto.getCarteGrise());
        vehicule.setRaison(dto.getRaison());
        vehicule.setMarque(dto.getMarque());
        vehicule.setTypevehicule(dto.getTypeVehicule());

        return vehicule;
    }

    @Override
    public Vehicule dtoToVehiculeUpdate(VehiculeDtoZ dto) {
        //Typevehicule typevehicule = typevehiculeService.findById(dto.getTypevehicule());
        //Marque marque = marqueService.findById(dto.getMarque());
        //Couleur couleur = Couleur.valueOf(dto.getCouleur());

        Vehicule vehicule = vehiculeRepository.getById(dto.getIdVehicule());
        vehicule.setIdVehicule(dto.getIdVehicule());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setCouleur(dto.getCouleur());
        vehicule.setStatutVehicule(StatutVehicule.DISPONIBLE);
        vehicule.setDateAchat(dto.getDateAchat());
        vehicule.setNumeroChassis(dto.getNumeroChassis());
        vehicule.setCarteGrise(dto.getCarteGrise());
        vehicule.setRaison(dto.getRaison());
        vehicule.setMarque(dto.getMarque());
        vehicule.setTypevehicule(dto.getTypeVehicule());

        return vehicule;
    }

    @Override
    public VehiculeDtoOut vehiculeToDto(Vehicule vehicule) {

        VehiculeDtoOut dto = new VehiculeDtoOut();
        dto.setId(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setCouleur(vehicule.getCouleur().name());
        dto.setStatutVehicule(vehicule.getStatutVehicule().name());
        dto.setCarteGrise(vehicule.getCarteGrise());
        dto.setNumeroChassis(vehicule.getNumeroChassis());
       // dto.setDateAchat(vehicule.getDateAchat().toString());
        dto.setRaison(vehicule.getRaison());
        dto.setTypeVehicule(vehicule.getTypevehicule().getLibelle());
        dto.setMarque(vehicule.getMarque().getName());
        //dto.setCountdownTimer(vehicule.getCountdownTimer());

        return dto;
    }

    @Override
    public List<VehiculeDtoOut> listVehiculesToDto(List<Vehicule> vehicules) {

        List<VehiculeDtoOut> dtos = new ArrayList<>();

        for (Vehicule vehicule : vehicules){

            VehiculeDtoOut dto = new VehiculeDtoOut();
            dto = this.vehiculeToDto(vehicule);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<Vehicule> all() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Vehicule introuvable avec l'identifiant :  " + id)
        );
    }



    @Override
    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule raison(VehiculeDtoZ dto) {



        Vehicule vehicule = vehiculeRepository.getById(dto.getIdVehicule());

        vehicule.setIdVehicule(dto.getIdVehicule());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setCouleur(dto.getCouleur());
        vehicule.setStatutVehicule(StatutVehicule.INDISPONIBLE);
        vehicule.setDateAchat(dto.getDateAchat());
        vehicule.setNumeroChassis(dto.getNumeroChassis());
        vehicule.setCarteGrise(dto.getCarteGrise());
        vehicule.setRaison(dto.getRaison());
        //vehicule.setCountdownTimer(dto.getCountdownTimer());
        vehicule.setMarque(dto.getMarque());
        vehicule.setTypevehicule(dto.getTypeVehicule());
        //vehicule.updateCountdownTimer();
        //vehicule.formatDuration(dto.getCountdownTimer());

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule disponible(Long id, Vehicule vehicule) {
        return null;
    }

    @Transactional
    public void disponible(Long id) {
        Vehicule vehicule = findById(id);
        if (vehicule != null) {
            // Logique pour rendre le véhicule disponible

            vehiculeRepository.save(vehicule);
        }
    }

    @Override
    public Vehicule indisponible(Long id, Vehicule vehicule) {
        Vehicule vehicule2 = this.findById(id);

        vehicule2.setStatutVehicule(StatutVehicule.INDISPONIBLE);
        return vehiculeRepository.save(vehicule2);
    }

    @Override
    public Vehicule reparation(Long id, Vehicule vehicule) {
        Vehicule vehicule2 = this.findById(id);

        vehicule2.setStatutVehicule(StatutVehicule.REPARATION);
        return vehiculeRepository.save(vehicule2);
    }

    @Override
    public void delete(Vehicule vehicule) {
        vehiculeRepository.delete(vehicule);
    }

    @Override
    public Vehicule findByImmatriculation(String immatriculation) {
        return vehiculeRepository.findByImmatriculation(immatriculation);
    }

    @Override
    public Vehicule findByCarteGrise(String carteGrise) {
        return vehiculeRepository.findByCarteGrise(carteGrise);
    }

    @Override
    public List<Vehicule> findVehiculesByTypevehicule(Typevehicule typevehicule) {
        return findVehiculesByTypevehicule(typevehicule);
    }

    @Override
    public List<Vehicule> findVehiculesByMarque(Marque marque) {
        return vehiculeRepository.findVehiculesByMarque(marque);
    }

    @Override
    public List<Vehicule> findVehiculesByCouleur(Couleur couleur) {
        return vehiculeRepository.findVehiculesByCouleur(couleur) ;
    }
    @Override
    public void deleteReferences(Long vehiculeId) {

        attributionRepository.deleteByVehiculeId(vehiculeId);
    }

    @Override
    public List<Vehicule> findVehiculesByDateAchat(LocalDate dateAchat) {
        return vehiculeRepository.findVehiculesByDateAchat(dateAchat);
    }



    @Override
    public RapportVehiculeDto asDto(Vehicule vehicule) {

        RapportVehiculeDto dto = new RapportVehiculeDto();
        dto.setId(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        return dto;
    }

    @Override
    public List<RapportVehiculeDto> asListDto(List<Vehicule> vehicules) {

        List<RapportVehiculeDto> dtos = new ArrayList<>();

        for (Vehicule vehicule : vehicules){
            RapportVehiculeDto dto = this.asDto(vehicule);
            dtos.add(dto);
        }

        return dtos;
    }


    @Override  //AFFICHER QUE LES VEHICULES DISPONIBLE
    public List<Vehicule> findVehiculesByStatutVehicule(StatutVehicule statutVehicule) {
        return vehiculeRepository.findVehiculesByStatutVehicule(statutVehicule);
    }
}
