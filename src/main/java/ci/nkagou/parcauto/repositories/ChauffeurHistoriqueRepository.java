package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.ChauffeurHistorique;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChauffeurHistoriqueRepository extends JpaRepository <ChauffeurHistorique, Long> {

    List<ChauffeurHistorique> findByDateParcoursBetweenAndVehiculeChauffeurAttVehicule(LocalDateTime debut, LocalDateTime fin, Vehicule vehicule);

    List<ChauffeurHistorique> findByDateParcoursBetweenAndVehiculeChauffeurAttEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}
