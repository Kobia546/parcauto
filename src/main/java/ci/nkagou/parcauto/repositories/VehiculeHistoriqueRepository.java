package ci.nkagou.parcauto.repositories;

import ci.nkagou.parcauto.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VehiculeHistoriqueRepository extends JpaRepository<VehiculeHistorique, Long> {

    List<VehiculeHistorique> findByDateParcoursBetweenAndVehiculeAttVehicule(LocalDateTime debut,LocalDateTime fin,Vehicule vehicule);

    //List<VehiculeHistorique> findByDateParcoursBetweenAndVehiculeAttEmployeDmdEmploye(LocalDateTime debut, LocalDateTime fin, Employe employe);
}