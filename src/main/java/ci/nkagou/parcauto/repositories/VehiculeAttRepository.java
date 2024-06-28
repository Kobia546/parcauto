package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.Attribution;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import ci.nkagou.parcauto.entities.VehiculeAtt;
import ci.nkagou.parcauto.enums.StatutAttrib;
import ci.nkagou.parcauto.enums.TypeAttribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface VehiculeAttRepository extends AttributionBaseRepository <VehiculeAtt> {


    List<VehiculeAtt> findAllByVehicule(Vehicule vehicule);
    List<VehiculeAtt> findAllByDateArriveeBetween(LocalDateTime debut, LocalDateTime fin);

    List<VehiculeAtt> findAllByDateAttributionBetween(LocalDateTime debut, LocalDateTime fin);

    List<VehiculeAtt> findAllByDateAttributionBetweenAndVehicule(LocalDateTime debut, LocalDateTime fin, Vehicule vehicule);

    List<Attribution> findByDateAttributionBetweenAndVehicule(LocalDateTime debut, LocalDateTime fin, Vehicule vehicule);

    //List<Attribution> findByDateAttributionBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

    //List<Attribution> findAttributionsByDateAttributionBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}
