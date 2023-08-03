package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Marque;
import ci.nkagou.parcauto.entities.Typevehicule;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.enums.Couleur;
import ci.nkagou.parcauto.enums.StatutVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeRepository  extends JpaRepository<Vehicule, Long> {

    Vehicule findByImmatriculation(String immatriculation);
    Vehicule findByCarteGrise(String carteGrise);
    List<Vehicule> findVehiculesByTypevehicule(Typevehicule typevehicule);
    List<Vehicule> findVehiculesByMarque(Marque marque);
    List<Vehicule> findVehiculesByCouleur(Couleur couleur);
    List<Vehicule> findVehiculesByDateAchat(LocalDate dateAchat);

    List<Vehicule> findVehiculesByStatutVehicule(StatutVehicule statutVehicule);
}
