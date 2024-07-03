package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.*;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.AttributionRepository;
import ci.nkagou.parcauto.repositories.DetailCarburantARepository;
import ci.nkagou.parcauto.repositories.DmdRepository;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.services.DestinationService;
import ci.nkagou.parcauto.services.DmdService;
import ci.nkagou.parcauto.services.EmployeService;
import ci.nkagou.parcauto.services.MotifService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ci.nkagou.parcauto.enums.Statut.*;
import static ci.nkagou.parcauto.enums.StatutAttrib.EN_ATTENTE;
import static ci.nkagou.parcauto.enums.TypeAttribution.ORIENTATION_TRANSPORT;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DmdServiceImpl implements DmdService {

    private DmdRepository dmdRepository;
    private EmployeService employeService;
    private EmployeDmdRepository employeDmdRepository;
    private MotifService motifService;
    private DestinationService destinationService;
    private AttributionRepository attributionRepository;
    private DetailCarburantARepository detailCarburantARepository;


    @Override
    public List<EmployeDmd> listEmployeDmdByStatutDirection(Statut statut, Direction directionResponsable) {

        List<EmployeDmd> ep = employeDmdRepository.findEmployeDmdsByStatut(statut);

        List<EmployeDmd> out = new ArrayList<>();

        for (EmployeDmd employeDmd : ep){

            Direction d = employeDmd.getEmploye().getDirection();

            if (directionResponsable.getIdDirection().equals(d.getIdDirection())){

                out.add(employeDmd);
            }

        }
        return out;
    }



    @Override
    public List<EmployeDmd> listEmployeDmdByStatutStatutDirection(Statut statut, Statut statuts, Direction directionResponsable) {

        List<EmployeDmd> ep = employeDmdRepository.findEmployeDmdsByStatut(statut);

        List<EmployeDmd> eps = employeDmdRepository.findEmployeDmdsByStatut(statuts);

        List<EmployeDmd> out = new ArrayList<>();

        for (EmployeDmd employeDmd : ep){

            Direction d = employeDmd.getEmploye().getDirection();

            if (directionResponsable.getIdDirection().equals(d.getIdDirection())){

                out.add(employeDmd);
            }

        }

        for (EmployeDmd employeDmd : eps){

            Direction ds = employeDmd.getEmploye().getDirection();

            if (directionResponsable.getIdDirection().equals(ds.getIdDirection())){

                out.add(employeDmd);
            }

        }
        return out;
    }

    /*@Override
    public List<EmployeDmd> listEmployeDmdByStatutStatutDirection(Statut statut, Statut statuts, Direction directionResponsable) {
        List<EmployeDmd> ep = employeDmdRepository.findEmployeDmdsByStatutAndStatut(statut,statuts);

        List<EmployeDmd> out = new ArrayList<>();

        for (EmployeDmd employeDmd : ep){

            Direction d = employeDmd.getEmploye().getDirection();

            if (directionResponsable.getIdDirection().equals(d.getIdDirection())){

                out.add(employeDmd);
            }

        }
        return out;
    }*/

    /*@Override
    public List<EmployeDmd> listEmployeDmdByEmployeDirection(Direction directionResponsable) {

        return employeDmdRepository.findEmployeDmdsByEmployeAndDirection(directionResponsable);
    }*/

    @Override
    public List<EmployeDmd> listEmployeDmdsByStatut(Statut statut) {
        return employeDmdRepository.findEmployeDmdsByStatut(statut);
    }

    @Override
    public DmdUserDtoOut dmdUserToDto(EmployeDmd dmd) {

        DmdUserDtoOut dto = new DmdUserDtoOut();
        dto.setId(dmd.getIdEmployeDmd());
        dto.setDatePrevue(dmd.getDmd().getDatePrevue().toString());
        dto.setHeurePrevue(dmd.getDmd().getHeurePrevue().toString());
        dto.setMoyenDemande(dmd.getDmd().getMoyenDemande().name());
        dto.setMotif(dmd.getMotif().getNomMotif());
        dto.setDestination(dmd.getDestination().getNomDestination());
        dto.setDateOperation(dmd.getDmd().getDateOperation().toString().replace("T", " "));
        dto.setStatut(dmd.getStatut().name());
        dto.setNomComplet(dmd.getEmploye().toNomComplet());
        dto.setObservation(dmd.getObservation());



        /*dto.setMatriculeEmploye(dmd.getEmploye().getNumMatEmpl().toString());*/
        /*dto.setNomComplet(employeService.getNomComplet(dmd.getEmploye()));*/
        return dto;

    }

    @Override
    public DmdParcDtoOut dmdParcToDto(EmployeDmd dmd) {
        DmdParcDtoOut dto= new DmdParcDtoOut();
        dto.setId(dmd.getIdEmployeDmd());
        dto.setDatePrevue(dmd.getDmd().getDatePrevue().toString());
        dto.setHeurePrevue(dmd.getDmd().getHeurePrevue().toString());
        dto.setMoyenDemande(dmd.getDmd().getMoyenDemande().name());
        dto.setDateOperation(dmd.getDmd().getDateOperation().toString());


        return dto;
    }

    @Override
    public List<DmdUserDtoOut> listDmdsToDto(List<EmployeDmd> dmds) {

        List<DmdUserDtoOut> dtos = new ArrayList<>();
        for (EmployeDmd dmd : dmds)
        {
            DmdUserDtoOut dto = new DmdUserDtoOut();

            dto = this.dmdUserToDto(dmd);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<DmdParcDtoOut> listDmdsParcToDto(List<EmployeDmd> dmds) {
        List<DmdParcDtoOut> dtos = new ArrayList<>();
        for (EmployeDmd dmd : dmds)
        {
            DmdParcDtoOut dto = new DmdParcDtoOut();

            dto = this.dmdParcToDto(dmd);
            dtos.add(dto);
        }
        return dtos;
    }

    /*@Override
    public List<DmdUserDtoOut> getById(List<Long> id) {
        List<EmployeDmd> dmds = employeDmdRepository.findByDmdIn(id);
        return listDmdsToDto(dmds);
    }*/


    @Override
    public List<EmployeDmd> all() {
        return employeDmdRepository.findAll();
    }

    @Override
    public EmployeDmd findById(Long id) {
        return employeDmdRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Dmd introuvable avec l'identifiant :  " + id)
        );
    }




    @Override
    public List<EmployeDmd> findEmployeDmdsByEmploye(Employe employe) {
        return employeDmdRepository.findEmployeDmdsByEmploye(employe);
    }

    @Override
    public List<EmployeDmd> findEmployeDmdsByDmdMoyenDemandeStatut(MoyenDemande moyenDemande,Statut statut) {
        return employeDmdRepository.findEmployeDmdsByDmdMoyenDemandeAndStatut(moyenDemande,statut);
    }

    /*@Override
    public List<EmployeDmd> findEmployeDmdsById(List<Long> id) {
        return employeDmdRepository.findByDmdIn(id);
    }*/

    @Override
    public EmployeDmd createDmdUser(DmdUserDto dto) {

        //Initit Dmd
        Dmd dmd = new Dmd();

        //Set Value
        dmd.setDatePrevue(dto.getDatePrevue());
        dmd.setHeurePrevue(dto.getHeurePrevue());
        dmd.setMoyenDemande(MoyenDemande.valueOf(dto.getMoyenDemande()));
        dmd.setDateOperation(LocalDateTime.now());
        //Create Dmd on Database.
        Dmd dmdPersist = dmdRepository.save(dmd);

        //Set Value
        EmployeDmd employeDmd = new EmployeDmd();
        employeDmd.setMotif(dto.getMotif());
        employeDmd.setDestination(dto.getDestination());
        employeDmd.setStatut(DEMANDE);
        employeDmd.setResponsable(dto.getEmploye().getIdEmploye());
        employeDmd.setDmd(dmdPersist);
        employeDmd.setEmploye(dto.getEmploye());

        //Create EmployeDmd on Database.
        employeDmdRepository.save(employeDmd);
        return employeDmd;
    }

    @Override
    public EmployeDmd annulerDmdUser(DmdUserDto dto) {

        EmployeDmd employeDmd = this.findById(dto.getId());
        Dmd dmd = dmdRepository.getById(employeDmd.getDmd().getIdDmd());

        dmd.setDatePrevue(dto.getDatePrevue());
        dmd.setHeurePrevue(dto.getHeurePrevue());
        dmd.setDateOperation(LocalDateTime.now());
        dmd.setMoyenDemande(MoyenDemande.valueOf(dto.getMoyenDemande()));

        employeDmd.setMotif(dto.getMotif());
        employeDmd.setDestination(dto.getDestination());
        employeDmd.setStatut(REFUS);
        employeDmd.setObservation(dto.getObservation());
        return employeDmdRepository.save(employeDmd);
    }

    @Override
    public void createDmdParc(DmdParcDtoW dto) {
        Dmd dmd = new Dmd();

        //Set Value
        dmd.setDatePrevue(dto.getDatePrevue());
        dmd.setHeurePrevue(dto.getHeurePrevue());
        dmd.setMoyenDemande(MoyenDemande.valueOf(dto.getMoyenDemande()));
        dmd.setDateOperation(LocalDateTime.now());

        //Create Dmd on Database.
        Dmd dmdPersist = dmdRepository.save(dmd);


        List<EmployeDmdDto> employeDmdDtos = dto.getEmployeDmdDto();

        for (EmployeDmdDto employeDmdDto : employeDmdDtos){
            //Set Value
            EmployeDmd employeDmd = new EmployeDmd();
            String nomPrenom = employeDmdDto.getNom();
            String [] nomprenoms = nomPrenom.split(" ");
            String nom = nomprenoms[1];
            String prenom = nomprenoms[0];

            Employe employe = employeService.findByNomPrenom(nom, prenom);
            String motif1 = employeDmdDto.getMotif();
            Motif motif = motifService.findByNomMotif(motif1);
            String destination1 = employeDmdDto.getDestination();
            Destination destination = destinationService.findByNomDestination(destination1);
            employeDmd.setEmploye(employe);
            employeDmd.setDmd(dmdPersist);
            employeDmd.setStatut(VALIDATION);
            employeDmd.setMotif(motif);
            employeDmd.setDestination(destination);
            //employeDmd.setResponsable(dto.getIdResponsable());
            /*employeDmd.setResponsable(dto.getEmploye().getIdEmploye());*/
            employeDmdRepository.save(employeDmd);

        }

    }

    @Override
    public EmployeDmd updateDmdUser(DmdUserDto dto) {

        EmployeDmd employeDmd = this.findById(dto.getId());
        Dmd dmd = dmdRepository.getById(employeDmd.getDmd().getIdDmd());
        dmd.setDatePrevue(dto.getDatePrevue());
        dmd.setHeurePrevue(dto.getHeurePrevue());
        dmd.setDateOperation(LocalDateTime.now());
        dmd.setMoyenDemande(MoyenDemande.valueOf(dto.getMoyenDemande()));
        employeDmd.setMotif(dto.getMotif());
        employeDmd.setDestination(dto.getDestination());
        employeDmd.setStatut(dto.getStatut());

        //employeDmd.setEmploye(employeService.getNomComplet(employeDmd.getEmploye()));
        return employeDmdRepository.save(employeDmd);
    }

   /* @Override
    public EmployeDmd updateDmdUserTransport(DmdUserDto dto) {
        EmployeDmd employeDmd = this.findById(dto.getId());
        Dmd dmd = dmdRepository.getById(employeDmd.getDmd().getIdDmd());
        dmd.setDatePrevue(dto.getDatePrevue());
        dmd.setHeurePrevue(dto.getHeurePrevue());
        dmd.setDateOperation(LocalDateTime.now());
        dmd.setMoyenDemande(MoyenDemande.TRANSPORT);
        Dmd dmd1 = dmdRepository.save(dmd);

        employeDmd.setMotif(dto.getMotif());
        employeDmd.setDestination(dto.getDestination());
        employeDmd.setStatut(ANNULER);
        employeDmd.setDmd(dmd1);

        //employeDmd.setEmploye(employeService.getNomComplet(employeDmd.getEmploye()));
        return employeDmdRepository.save(employeDmd);

    }*/

    @Override
    public EmployeDmd updateDmdParc(DmdParcDto dto) {
        return null;
    }

    @Override
    public void updateDmdUserAttribution(DmdUserDtoZ dto) {

        for (DmdUserDto dmdUserDto : dto.getDmdUserDto()) {

            // Get the existing EmployeDmd instance by its ID
            EmployeDmd existingEmployeDmd = employeDmdRepository.findById(dmdUserDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("EmployeDmd not found with ID " + dmdUserDto.getId()));

            // Get the corresponding Dmd instance and update its properties
            Dmd dmd = existingEmployeDmd.getDmd();
            dmd.setDatePrevue(dmdUserDto.getDatePrevue());
            dmd.setHeurePrevue(dmdUserDto.getHeurePrevue());
            dmd.setDateOperation(LocalDateTime.now());
            dmd.setMoyenDemande(MoyenDemande.valueOf(dmdUserDto.getMoyenDemande()));
            Dmd dmd1 = dmdRepository.save(dmd);

            // Update the EmployeDmd instance with the new properties
            existingEmployeDmd.setMotif(dmdUserDto.getMotif());
            existingEmployeDmd.setDestination(dmdUserDto.getDestination());
            existingEmployeDmd.setStatut(dmdUserDto.getStatut());
            existingEmployeDmd.setEmploye(dmdUserDto.getEmploye());
            //existingEmployeDmd.setVehicule(dmdUserDto.getVehicule());
            existingEmployeDmd.setDmd(dmd1);


            // Save the updated EmployeDmd and Dmd instances
            employeDmdRepository.save(existingEmployeDmd);

        }
    }



    @Override
    public void delete(EmployeDmd employeDmd) {

        employeDmdRepository.delete(employeDmd);
    }

    @Override
    public DmdUserDto createDmdUserDto(EmployeDmd dmd) {

        DmdUserDto dto = new DmdUserDto();

        dto.setId(dmd.getIdEmployeDmd());
        dto.setDatePrevue(dmd.getDmd().getDatePrevue());
        dto.setHeurePrevue(dmd.getDmd().getHeurePrevue());
        dto.setMoyenDemande(dmd.getDmd().getMoyenDemande().name());
        String motif1 = dmd.getMotif().getNomMotif();
        Motif motif = motifService.findByNomMotif(motif1);
        dto.setMotif(motif);
        String destination1 = dmd.getDestination().getNomDestination();
        Destination destination = destinationService.findByNomDestination(destination1);
        dto.setDestination(destination);
        dto.setStatut(dmd.getStatut());
        dto.setResponsable(dmd.getResponsable());
        dto.setEmploye(dmd.getEmploye());

        return dto;
    }

    @Override
    public EmployeDmd findByEmploye(Employe employe) {
        return employeDmdRepository.findByEmploye(employe);
    }

    @Override
    public EmployeDmd validerDmd(Long id, Employe employe) {

        EmployeDmd employeDmd = this.findById(id);

        if (employeDmd.getDmd().getMoyenDemande().name().equals(MoyenDemande.ORIENTATION_TRANSPORT.name())){

            //implementation de transport
            //AttributionCarburantAttDto dto = new AttributionCarburantAttDto();

            //Creation de Attribution Carburant
            CarburantAtt carburantAtt = new CarburantAtt();
            carburantAtt.setDateAttribution(LocalDateTime.now());
            carburantAtt.setStatutAttrib(EN_ATTENTE);
            carburantAtt.setTypeAttribution(ORIENTATION_TRANSPORT);
            CarburantAtt carburantAtt1 = attributionRepository.save(carburantAtt);

            //Mise a jour de EmployeDmd
            employeDmd.setStatut(Statut.ATTRIBUTION);
            EmployeDmd employeDmd1 = employeDmdRepository.save(employeDmd);

            //Creation de detailCarburantAttribution
            DetailCarburantA detail = new DetailCarburantA();
            detail.setAttribution(carburantAtt1);
            detail.setEmployeDmd(employeDmd1);
            DetailCarburantA detailCarburantA = detailCarburantARepository.save(detail);


            return employeDmd1;
        }else {

            //implementation de vehicule et vehicule + chauffeur
            employeDmd.setStatut(VALIDATION);
            employeDmd.setResponsable(employe.getIdEmploye());
            EmployeDmd dmd = employeDmdRepository.save(employeDmd);
            return dmd;

        }



    }

    @Override
    public EmployeDmd annulerDmd(Long id, EmployeDmd employeDmd) {
        EmployeDmd employeDmd2 = this.findById(id);
        employeDmd.setStatut(ANNULER);
        EmployeDmd dmd = employeDmdRepository.save(employeDmd2);
        return dmd;
    }

    @Override
    public EmployeDmd findByid(Long id) {
        return null;
    }


    @Override
    public   EmployeDmd findByidEmployeDmdIn(Long idEmployeDmd) {
        return this.employeDmdRepository.getById(idEmployeDmd);
    }


    @Override
    public EmployeDmd findByIdStatut(Long id, Statut statut) {
        EmployeDmd employeDmd = this.findById(id);
        //employeDmd.setStatut(DEMANDE);
        //employeDmd.setResponsable(employe.getIdEmploye());
        EmployeDmd dmd = employeDmdRepository.save(employeDmd);
        return dmd;
    }


}
