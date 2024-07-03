package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.dmd.AttributionDtoOut;
import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportEmployeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.StatutChauffeur;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.repositories.UserRepository;
import ci.nkagou.parcauto.services.EmployeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private EmployeRepository employeRepository;
    private UserRepository userRepository;

    @Override
    public List<Employe> all() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employé introuvable avec l'identifiant :  " + id)
        );
    }

    @Override
    public EmployeDtoOut employeToDto(Employe employe) {

        EmployeDtoOut dto = new EmployeDtoOut();

        dto.setId(employe.getIdEmploye());
        dto.setNom(employe.toNomComplet());
        dto.setNumMatEmpl(employe.getNumMatEmpl().toString());
        dto.setGenre(employe.getGenre().toString());
        dto.setTelephoneEmploye(employe.getTelephoneEmploye());
        dto.setDateNaissance(employe.getDateNaissance().toString());
        dto.setStatutChauffeur(employe.getStatutChauffeur().toString());
        return dto;
    }

    @Override
    public List<EmployeDtoOut> listEmployesToDto(List<Employe> employes) {

        List<EmployeDtoOut> dtos = new ArrayList<>();

        for (Employe employe : employes)
        {
            EmployeDtoOut dto = new EmployeDtoOut();

            dto = this.employeToDto(employe);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public List<Employe> findEmployesByEstUtilisateur(Boolean estUtilisateur) {
        return employeRepository.findEmployesByEstUtilisateur(estUtilisateur);
    }

    @Override
    public Employe findByMatricule(Long matricule) {
        return employeRepository.findByNumMatEmpl(matricule);
    }

    @Override
    public Employe findByEmail(String email) {
        return employeRepository.findByEmail(email);
    }

    @Override
    public Employe create(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe getEmployeByUserName(String username) {
        AppUser user = userRepository.findByUserName(username);

       // Employe employe = user.getEmploye();
        return user.getEmploye();
    }

   /* @Override
    public Employe findEmployeByAppUser(AppUser user) {
        return employeRepository.findEmployeByAppUser(user);
    }*/


    @Override
    public void delete(Employe employe) {
        employeRepository.delete(employe);
    }

    @Override
    public String getNomComplet(Employe employe) {
        String nomComplet = employe.getPrenom() + " " + employe.getNom();
        return nomComplet;
    }

    @Override
    public List<Employe> listEmployesEstSuperieurHierachique(Boolean estResponsable) {
        List<Employe> employes = employeRepository.findEmployesByEstSuperieureHierachique(estResponsable);
        return employes;
    }

    @Override
    public Employe findByNomPrenom(String nom, String prenom) {
        return employeRepository.findByNomAndPrenom(nom,prenom);
    }

    @Override
    public List<Employe> findEmployesByEstChauffeur(Boolean estChauffeur) {
        return employeRepository.findEmployesByEstChauffeur(estChauffeur);
    }

    @Override
    public Employe disponible(Long id, Employe employe) {

        Employe employe1 = employeRepository.getById(id);
        employe1.setStatutChauffeur(StatutChauffeur.DISPONIBLE);


        return employeRepository.save(employe1);
    }

    @Override
    public Employe indisponible(Long id, Employe employe) {

        Employe employe1 = employeRepository.getById(id);
        employe1.setStatutChauffeur(StatutChauffeur.INDISPONIBLE);

        return employeRepository.save(employe1);
    }

    @Override
    public List<Employe> findEmployesEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur) {
        return employeRepository.findEmployesByEstChauffeurAndStatutChauffeur(estChauffeur,statutChauffeur);
    }

    @Override
    public Employe findByDirectionEstSuperieurHirarchique(Direction direction, boolean estSuperieurHirarchique) {
        return employeRepository.findByDirectionAndEstSuperieureHierachique(direction,estSuperieurHirarchique);
    }

    @Override
    public RapportChauffeurDto asDto(Employe employe) {
        RapportChauffeurDto dto = new RapportChauffeurDto();

        dto.setId(employe.getIdEmploye());
        dto.setNom(employe.toNomComplet());

        return dto;
    }

    @Override
    public RapportEmployeDto asDtos(Employe employe) {
        RapportEmployeDto dto = new RapportEmployeDto();

        dto.setId(employe.getIdEmploye());
        dto.setNom(employe.toNomComplet());

        return dto;
    }

    @Override
    public List<RapportChauffeurDto> listRapportChauffeur(List<Employe> employes) {

        List<RapportChauffeurDto> dtos = new ArrayList<>();

        for(Employe employe : employes){
            RapportChauffeurDto dto = this.asDto(employe);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<RapportEmployeDto> listRapportEmploye(List<Employe> employes) {

        List<RapportEmployeDto> dtos = new ArrayList<>();

        for(Employe employe : employes){
            RapportEmployeDto dto = this.asDtos(employe);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<Employe> listSuperieurByEmploye(Employe employe) {
        Direction direction = employe.getDirection();

        List<Employe> employes = employeRepository.findAllByDirection(direction);

        //List des employe appartenant a une direction et est superieur hierachique
        List<Employe> eps = new ArrayList<>();

        for (Employe e : employes){

            if (e.isEstSuperieureHierachique()){
                eps.add(e);
            }
        }

        return eps;
    }

    @Override
    public List<String> listEmailsSuperieur(Employe employe) {

        List<Employe> superieurs = this.listSuperieurByEmploye(employe);
        List<String> emailSuperieurs = new ArrayList<>();
        for (Employe e : superieurs){
            emailSuperieurs.add(e.getEmail());
        }
        return emailSuperieurs;
    }

    @Override
    public List<Employe> listParcAuto() {


        return null;
    }

    /*@Override
    public List<Employe> findEmployesByEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur) {
        return employeRepository.findEmployesByEstChauffeurStatutChauffeur(estChauffeur, statutChauffeur);
    }*/


}
