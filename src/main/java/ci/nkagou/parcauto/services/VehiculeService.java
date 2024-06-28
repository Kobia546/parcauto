package ci.nkagou.parcauto.services;

import ci.nkagou.parcauto.dtos.rapport.RapportVehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDto;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoOut;
import ci.nkagou.parcauto.dtos.vehicule.VehiculeDtoZ;
import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeService {

    Vehicule dtoToVehicule(VehiculeDtoZ dto);
    Vehicule dtoToVehiculeUpdate(VehiculeDtoZ dto);

    VehiculeDtoOut vehiculeToDto (Vehicule vehicule);
    List<VehiculeDtoOut> listVehiculesToDto(List<Vehicule> vehicules);
    List<Vehicule> all();
    Vehicule findById(Long id);
    Vehicule create (Vehicule vehicule);
    Vehicule update (Vehicule vehicule);
    Vehicule raison (VehiculeDtoZ dto);

    Vehicule disponible(Long id,Vehicule vehicule);
    Vehicule indisponible(Long id,Vehicule vehicule);
    Vehicule reparation(Long id,Vehicule vehicule);

    void delete(Vehicule vehicule);

    Vehicule findByImmatriculation(String immatriculation);
    Vehicule findByCarteGrise(String carteGrise);
    List<Vehicule> findVehiculesByTypevehicule(Typevehicule typevehicule);
    List<Vehicule> findVehiculesByMarque(Marque marque);
    List<Vehicule> findVehiculesByCouleur(Couleur couleur);
    List<Vehicule> findVehiculesByStatutVehicule(StatutVehicule statutVehicule);

    void deleteReferences(Long vehiculeId);

    List<Vehicule> findVehiculesByDateAchat(LocalDate dateAchat);

    RapportVehiculeDto asDto(Vehicule vehicule);
    List<RapportVehiculeDto> asListDto(List<Vehicule> vehicules);

}
