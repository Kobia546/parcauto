package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.chauffeur.EmployeDtoOut;
import ci.nkagou.parcauto.dtos.employe.EmployeRequest;
import ci.nkagou.parcauto.dtos.employe.EmployeResponse;
import ci.nkagou.parcauto.dtos.rapport.RapportChauffeurDto;
import ci.nkagou.parcauto.dtos.rapport.RapportEmployeDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.Genre;
import ci.nkagou.parcauto.enums.StatutChauffeur;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.repositories.UserRepository;
import ci.nkagou.parcauto.services.EmployeService;
import ci.nkagou.parcauto.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeServiceImpl implements EmployeService {

    private EmployeRepository employeRepository;
    private UserRepository userRepository;
    private UserService userService;

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
    public Employe createOrUpdateEmploye(EmployeDtoOut employeDto) {
        Employe employe =new Employe();

//        if (employeDto.getNumMatEmpl() != null) {
//            employe = employeRepository.findByNumMatEmpl(Long.valueOf(employeDto.getNumMatEmpl()));
//            if (employe == null) {
//                throw new ResourceNotFoundException("Employé introuvable avec le matricule : " + employeDto.getNumMatEmpl());
//            }
////        } else {
////            employe = new Employe();
////        }

        employe.setNumMatEmpl(Long.valueOf(employeDto.getNumMatEmpl()));
        employe.setNom(employeDto.getNom());
        employe.setPrenom(employeDto.getPrenom());
        employe.setGenre(Genre.valueOf(employeDto.getGenre()));
        employe.setFonction(employeDto.getFonction());
        employe.setDateEmbauche(LocalDate.parse(employeDto.getDateEmbauche()));
        employe.setEmail(employeDto.getEmail());
        employe.setTelephoneEmploye(employeDto.getTelephoneEmploye());
        employe.setDateNaissance(LocalDate.parse(employeDto.getDateNaissance()));
        employe.setEstSuperieureHierachique(Boolean.parseBoolean(employeDto.getEstSuperieureHierachique()));
        employe.setEstChauffeur(Boolean.parseBoolean(employeDto.getEstChauffeur()));
        employe.setStatutChauffeur(StatutChauffeur.valueOf(employeDto.getStatutChauffeur()));
        employe.setEstUtilisateur(Boolean.parseBoolean(employeDto.getEstUtilisateur()));

        if (employeDto.getSite() != null) {
            Site site = employeRepository.findById(Long.valueOf(employeDto.getSite()))
                    .orElseThrow(() -> new ResourceNotFoundException("Site introuvable avec l'identifiant : " + employeDto.getSite())).getSite();
            employe.setSite(site);
        }

//        if (employeDto.getDirection() != null) {
//            Direction direction = employeRepository.findById(Long.valueOf(employeDto.getDirection()))
//                    .orElseThrow(() -> new ResourceNotFoundException("Direction introuvable avec l'identifiant : " + employeDto.getDirection())).getDirection();
            //employe.setDirection(employeDto.getDirection());


        return employeRepository.save(employe);
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

        AppRole role = userService.getRoleByName("ROLE_PARCAUTO");
        List<AppUser> users = userService.listUserByRole(role);
        List<Employe> employes = new ArrayList<>();
        for (AppUser user : users){
            employes.add(user.getEmploye());
        }
        return employes;
    }

    @Override
    public List<Employe> listMoyenGeneraux() {
        AppRole role = userService.getRoleByName("ROLE_MOYEN-GENERAUX");
        List<AppUser> users = userService.listUserByRole(role);
        List<Employe> employes = new ArrayList<>();
        for (AppUser user : users){
            employes.add(user.getEmploye());
        }
        return employes;
    }

    @Override
    public List<String> listEmailByListEmploye(List<Employe> employes) {

        List<String> emails = new ArrayList<>();
        for (Employe employe : employes){
            emails.add(employe.getEmail());
        }
        return emails;
    }

    @Override
    public EmployeResponse DTO(Employe employe) {

        EmployeResponse e = new EmployeResponse();
        e.setId(employe.getIdEmploye());
        e.setMatricule(String.valueOf(employe.getNumMatEmpl()));
        e.setNom(employe.getNom());
        e.setPrenoms(employe.getPrenom());
        e.setEmail(employe.getEmail());
        e.setGenre(employe.getGenre().name());
        e.setEstResponsable(EmployeResponse.stringEstResponsable(employe.isEstSuperieureHierachique()));
        e.setEstChauffeur(EmployeResponse.stringEstChauffeur(employe.isEstChauffeur()));
        e.setDirection(employe.getDirection().getLibelle());
        e.setSite(employe.getSite().getLibelle());
        e.setFonction(employe.getFonction());
         return  e;


//        return EmployeResponse.builder()
//                .id(employe.getIdEmploye())
//                .nom(employe.getNom())
//                .prenoms(employe.getPrenom())
//                .email(employe.getEmail())
//                .genre(employe.getGenre().name())
//                .matricule())
//                .direction(employe.getDirection().getLibelle())
//                .site(employe.getSite().getLibelle())
//                .fonction(employe.getFonction())
//                .estResponsable(EmployeResponse.stringEstResponsable(employe.isEstSuperieureHierachique()))
//                .estChauffeur(EmployeResponse.stringEstChauffeur(employe.isEstChauffeur()))
//                .build();

    }

    @Override
    public Employe ENTITY(EmployeRequest request) {

        Employe employe = new Employe();
        employe.setNumMatEmpl(request.getNumMatEmpl());
        employe.setNom(request.getNom());
        employe.setPrenom(request.getPrenom());
        employe.setEmail(request.getEmail());
        employe.setGenre(request.getGenre());
        employe.setDirection(request.getDirection());
        employe.setFonction(request.getFonction());
        employe.setSite(request.getSite());
        employe.setEstChauffeur(request.getEstChauffeur());
        employe.setEstSuperieureHierachique(request.getEstSuperieureHierachique());
        employe.setStatutChauffeur(StatutChauffeur.DISPONIBLE);
        employe.setEstSuperieureHierachique(false);
        return employe;
    }


    @Override
    public List<EmployeResponse> DTOS(List<Employe> employes) {

        List<EmployeResponse> responses = new ArrayList<>();
        for (Employe employe : employes){

            EmployeResponse response = this.DTO(employe);
            responses.add(response);
            String n = "";
        }

        return responses;
    }

    @Override
    public List<EmployeResponse> DTOS() {

        List<Employe> employes = employeRepository.findAll();
        String n = "";
        List<EmployeResponse> employeResponseList = this.DTOS(employes);
        String na = "";
        return employeResponseList;
        //return this.DTOS(employes);
    }

    @Override
    public void create(EmployeRequest request) {

        employeRepository.save(this.ENTITY(request));

    }
    @Override
    public void updateEntity(Employe employe, EmployeRequest request) {
        employe.setNumMatEmpl(request.getNumMatEmpl());
        employe.setNom(request.getNom());
        employe.setPrenom(request.getPrenom());
        employe.setEmail(request.getEmail());
        employe.setGenre(request.getGenre());
        employe.setDirection(request.getDirection());
        employe.setFonction(request.getFonction());
        employe.setSite(request.getSite());
        employe.setEstChauffeur(request.getEstChauffeur());
        employe.setEstSuperieureHierachique(request.getEstSuperieureHierachique());
    }

    @Override
    public void update(EmployeRequest request, Long idEmploye) {

//        Employe employe = this.getEmploye(idEmploye);
//
//        employe = this.ENTITY(request);
//
//        employeRepository.save(employe);
        Employe existingEmploye = this.getEmploye(idEmploye);
        this.updateEntity(existingEmploye, request);
        employeRepository.save(existingEmploye);

    }

    @Override
    public Employe getEmploye(Long id) {
        return employeRepository.getById(id);
    }

    @Override
    public void delete(Long id) {

        employeRepository.delete(this.getEmploye(id));

    }

    /*@Override
    public List<Employe> findEmployesByEstChauffeurStatutChauffeur(Boolean estChauffeur, StatutChauffeur statutChauffeur) {
        return employeRepository.findEmployesByEstChauffeurStatutChauffeur(estChauffeur, statutChauffeur);
    }*/


}
