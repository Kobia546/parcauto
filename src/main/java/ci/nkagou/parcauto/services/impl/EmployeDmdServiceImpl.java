package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dmd.EmployeEtatDto;
import ci.nkagou.parcauto.dtos.dmd.EmployeRapportDto;
import ci.nkagou.parcauto.dtos.dmd.EtatResponsableDto;
import ci.nkagou.parcauto.entities.*;
import ci.nkagou.parcauto.enums.MoyenDemande;
import ci.nkagou.parcauto.enums.RapportStatut;
import ci.nkagou.parcauto.enums.Selection;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.exceptions.ResourceNotFoundException;
import ci.nkagou.parcauto.repositories.DmdRepository;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.repositories.EmployeRepository;
import ci.nkagou.parcauto.repositories.UserRepository;
import ci.nkagou.parcauto.services.EmployeDmdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ci.nkagou.parcauto.enums.Statut.ANNULER;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeDmdServiceImpl implements EmployeDmdService {

    private EmployeDmdRepository employeDmdRepository;
    private UserRepository userRepository;
    private DmdRepository dmdRepository;
    private EmployeRepository employeRepository;

    @Override
    public List<EmployeDmd> all() {
        return employeDmdRepository.findAll();
    }

    @Override
    public EmployeDmd findById(Long id) {

        return employeDmdRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employe_Dmd introuvable avec l'identifiant :  " + id)
        );
    }

//    @Override
//    public EmployeDmd create(EmployeDmd employeDmd) {
//        return employeDmdRepository.save(employeDmd);
//    }
    @Override
public void create(EmployeDmd employeDmd, AppUser userConnecte) {
    // Définir la propriété user de l'entité EmployeDmd
    employeDmd.setUser(userConnecte);

    // Enregistrer l'entité EmployeDmd dans la base de données
    employeDmdRepository.save(employeDmd);
}

    @Override
    public EmployeDmd update(EmployeDmd employeDmd) {
        return employeDmdRepository.save(employeDmd);
    }

   /* @Override
    public EmployeDmd updateTransport(EmployeDmd employeDmd) {
        Dmd d = dmdRepository.getById(employeDmd.getDmd().getIdDmd());

        d.setMoyenDemande(MoyenDemande.TRANSPORT);
        Dmd d1 = dmdRepository.save(d);
        EmployeDmd dmd1 = employeDmdRepository.getById(employeDmd.getIdEmployeDmd());
        dmd1.setDmd(d1);
        dmd1.setStatut(ANNULER);
        EmployeDmd dmd2 = employeDmdRepository.save(dmd1);

        return dmd2;
    }*/

    @Override
    public void delete(EmployeDmd employeDmd) {
    employeDmdRepository.delete(employeDmd);
    }

    @Override
    public List<EmployeDmd> getListEmployeDmdByDmd(Dmd dmd) {

        return employeDmdRepository.findAllByDmd(dmd);

    }

    @Override
    public List<EmployeDmd> getListEmployeDmdByDmd(List<Dmd> dmds) {

        List<EmployeDmd> listDmd = new ArrayList<>();
        for (Dmd dmd : dmds){
            List<EmployeDmd> d = this.getListEmployeDmdByDmd(dmd);
            listDmd.addAll(d);
        }
        return listDmd;
    }

    @Override
    public List<EmployeDmd> getListEmployeDmdByDmdAndStatut(List<Dmd> dmds, Statut statut) {

        List<EmployeDmd> employeDmds = this.getListEmployeDmdByDmd(dmds);
        List<EmployeDmd> list = new ArrayList<>();

        for (EmployeDmd employeDmd : employeDmds){
            if (employeDmd.getStatut().equals(statut)) {
                list.add(employeDmd);
            }
        }
        return list;
    }

    @Override
    public List<EmployeDmd> getListEmployeDmdByDmdAndEmploye(List<Dmd> dmds, Employe employe) {
        List<EmployeDmd> employeDmds = this.getListEmployeDmdByDmd(dmds);
        List<EmployeDmd> list = new ArrayList<>();

        for (EmployeDmd employeDmd : employeDmds){
            //employe = employeDmd.getEmploye();
            if (employeDmd.getEmploye().equals(employe)) {
            list.add(employeDmd);
            }
        }
        return list;
    }


    /*@Override
    public List<EmployeDmd> getListEmployeDmdByDmdAndStatutAndDirection(List<Dmd> dmds,Statut statut, Direction direction) {
        List<EmployeDmd> employeDmds = this.getListEmployeDmdByDmd(dmds);
        List<EmployeDmd> list = new ArrayList<>();

        for (EmployeDmd employeDmd : employeDmds){
            //employe = employeDmd.getEmploye();
            if (employeDmd.getEmploye().getDirection().equals(direction)) {
                list.add(employeDmd);
            }

            if (employeDmd.getStatut().equals(statut)) {
                list.add(employeDmd);
            }

        }
        return list;
    }*/
    public List<EmployeDmd> getListEmployeDmdByDmdAndStatutAndDirection(List<Dmd> dmds, Statut statut, Direction direction) {
        List<EmployeDmd> employeDmds = this.getListEmployeDmdByDmd(dmds);

        List<EmployeDmd> filteredList = employeDmds.stream()
                .filter(employeDmd -> employeDmd.getStatut().equals(statut) &&
                        employeDmd.getEmploye().getDirection().equals(direction) &&
                        dmds.contains(employeDmd.getDmd()))
                .collect(Collectors.toList());

        return filteredList;
    }

    @Override
    public List<EmployeDmd> listEmployeDmdByDateBetweenAndStatut(LocalDate debut, LocalDate fin, Statut statut) {
        List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(debut, fin);
        List<EmployeDmd> employeDmds = this.getListEmployeDmdByDmdAndStatut(dmds, statut);
        return employeDmds;
    }

    /*@Override
    public List<EmployeDmd> listEmployeDmdByDateBetweenAndRapportStatut(EmployeRapportDto dto) {

        List<EmployeDmd> employeDmds = new ArrayList<>();
        if (dto.getRapportStatut().equals(RapportStatut.TOUS)){
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            employeDmds = this.getListEmployeDmdByDmd(dmds);

        }else {
            String rapportStatutString = dto.getRapportStatut().name();
            Statut statut = Statut.valueOf(rapportStatutString);
            employeDmds = this.listEmployeDmdByDateBetweenAndStatut(dto.getDebut(),dto.getFin(),statut);
        }
        return employeDmds;
    }*/

    @Override
    public List<EmployeDmd> listEmployeDmdByDateBetweenAndRapportStatut(EmployeRapportDto dto) {

        List<EmployeDmd> employeDmds = new ArrayList<>();
        if (dto.getRapportStatut() == RapportStatut.TOUS){
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            employeDmds = this.getListEmployeDmdByDmd(dmds);

        } else {
            String rapportStatutString = dto.getRapportStatut().name();
            Statut statut = Statut.valueOf(rapportStatutString);
            employeDmds = this.listEmployeDmdByDateBetweenAndStatut(dto.getDebut(),dto.getFin(),statut);
        }

        return employeDmds;
    }

    @Override
    public List<EmployeDmd> listEmployeDmdByDateBetweenAndEmploye(EmployeEtatDto dto) {
        List<EmployeDmd> employeDmds = new ArrayList<>();

        if (dto.getSelection() == Selection.TOUS){
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            employeDmds = this.getListEmployeDmdByDmd(dmds);
        } else {
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            employeDmds = this.getListEmployeDmdByDmdAndEmploye(dmds,dto.getEmploye());
        }
        return employeDmds;
    }

    @Override
    public List<EmployeDmd> listEmployeDmdByDateBetweenAndDirection(EtatResponsableDto dto) {

        List<EmployeDmd> employeDmds = new ArrayList<>();

        if (dto.getSelection() == Selection.TOUS){
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            employeDmds = this.getListEmployeDmdByDmd(dmds);
        } else {
            List<Dmd> dmds = dmdRepository.findAllByDatePrevueBetween(dto.getDebut(), dto.getFin());
            //employeDmds = this.getListEmployeDmdByDmdAndDirection(dmds,dto.getDirection());
        }
        return employeDmds;
    }

}
