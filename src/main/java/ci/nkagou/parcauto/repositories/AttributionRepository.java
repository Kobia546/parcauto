package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutAttrib;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttributionRepository extends JpaRepository<Attribution, Long> {

    //List<Attribution> findAttributionsByEmployeDmdEmploye(Employe employe);

    List<Attribution> findAllByDateAttributionBetween(LocalDateTime debut, LocalDateTime fin);

    List<Attribution> findAttributionsByDateAttributionBetween(LocalDateTime debut, LocalDateTime fin);
    List<Attribution> findAttributionsByDateDeDepartBetween(LocalDateTime debut, LocalDateTime fin);
    @Transactional
    void deleteByVehiculeId(Long vehiculeId);

    //List<Attribution> findAllByDateBetween(LocalDateTime , LocalDateTime );

    //List<VehiculeAtt> findByDateAttributionBetweenAndVehicule(LocalDateTime debut, LocalDateTime fin, Vehicule vehicule);

    //List<VehiculeChauffeurAtt> findByDateAttributionBetweenAndEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);

    //List<VehiculeChauffeurAtt> findByDateAttributionBetweenAndEmployeAndVehicule(LocalDateTime debut, LocalDateTime fin, Employe employe, Vehicule vehicule);


}
