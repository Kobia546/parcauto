package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutAttrib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface VehiculeChauffeurAttRepository extends AttributionBaseRepository <VehiculeChauffeurAtt> {


    List<Attribution> findByDateAttributionBetweenAndEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

    List<Attribution> findByDateAttributionBetweenAndEmployeAndVehicule(LocalDateTime debut, LocalDateTime fin, Employe employe, Vehicule vehicule);

    List<Attribution> findByDateAttributionBetweenAndVehicule(LocalDateTime debut, LocalDateTime fin, Vehicule vehicule);

    //List<Attribution> findAttributionsByDateAttributionBetweenAndEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

    //List<Attribution> findAttributionsByDateAttributionBetweenAndEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

}
