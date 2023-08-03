package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.vehicule.VehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoZ;
import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
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
    private VehiculeHistoriqueRepository vehiculeIndisponibleRepository;
    private TypevehiculeService typevehiculeService;
    private MarqueService marqueService;

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
        dto.setDateAchat(vehicule.getDateAchat().toString());
        dto.setRaison(vehicule.getRaison());
        dto.setTypeVehicule(vehicule.getTypevehicule().getLibelle());
        dto.setMarque(vehicule.getMarque().getName());

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
    public Vehicule raison(VehiculeDto dto) {

        Vehicule vehicule1 = vehiculeRepository.getById(dto.getId());
        Marque marque = marqueService.findById(dto.getMarque());
        Typevehicule typevehicule = typevehiculeService.findById(dto.getTypeVehicule());

        vehicule1.setIdVehicule(dto.getId());
        vehicule1.setImmatriculation(dto.getImmatriculation());
        vehicule1.setCouleur(Couleur.valueOf(dto.getCouleur()));
        vehicule1.setCarteGrise(dto.getCarteGrise());
        vehicule1.setStatutVehicule(StatutVehicule.INDISPONIBLE);
        vehicule1.setDateAchat(dto.getDateAchat());
        vehicule1.setMarque(marque);
        vehicule1.setTypevehicule(typevehicule);
        vehicule1.setRaison(dto.getRaison());

        return vehiculeRepository.save(vehicule1);
    }

    @Override
    public Vehicule disponible(Long id, Vehicule vehicule) {
        Vehicule vehicule2 = this.findById(id);

        vehicule2.setStatutVehicule(StatutVehicule.DISPONIBLE);
        return vehiculeRepository.save(vehicule2);
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
    public List<Vehicule> findVehiculesByDateAchat(LocalDate dateAchat) {
        return vehiculeRepository.findVehiculesByDateAchat(dateAchat);
    }



    @Override  //AFFICHER QUE LES VEHICULES DISPONIBLE
    public List<Vehicule> findVehiculesByStatutVehicule(StatutVehicule statutVehicule) {
        return vehiculeRepository.findVehiculesByStatutVehicule(statutVehicule);
    }
}
