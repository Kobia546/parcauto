package ci.nkagou.parcauto.services.impl;

import ci.nkagou.parcauto.dtos.dashboard.DashbordUserResponseDto;
import ci.nkagou.parcauto.dtos.dmd.EmployeRapportDto;
import ci.nkagou.parcauto.entities.AppUser;
import ci.nkagou.parcauto.entities.Employe;
import ci.nkagou.parcauto.enums.Statut;
import ci.nkagou.parcauto.repositories.EmployeDmdRepository;
import ci.nkagou.parcauto.repositories.UserRepository;
import ci.nkagou.parcauto.services.DashboardService;
import ci.nkagou.parcauto.services.EmployeDmdService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService  {
    private final EmployeDmdService employeDmdService;
    private final EmployeDmdRepository employeDmdRepository;
    private final UserRepository userRepository ;

    public DashboardServiceImpl(EmployeDmdService employeDmdService, EmployeDmdRepository employeDmdRepository, UserRepository userRepository) {
        this.employeDmdService = employeDmdService;
        this.employeDmdRepository = employeDmdRepository;

        this.userRepository = userRepository;
    }

    @Override
    public List<DashbordUserResponseDto> dashbordUserResponseDto(Principal principal, EmployeRapportDto EmployeRapportDto) {
        return null;
    }



    @Override
    public DashbordUserResponseDto getDmdCountByStatutForUser(Principal principal){
        String username = principal.getName();
        Employe employe = userRepository.findByUserName(username).getEmploye();

        long totale = employeDmdRepository.countByEmploye(employe);
        long annuler = employeDmdRepository.countByEmployeAndStatut(employe, Statut.ANNULER);
        long valider = employeDmdRepository.countByEmployeAndStatut(employe, Statut.VALIDATION);
        long refuser = employeDmdRepository.countByEmployeAndStatut(employe, Statut.REFUS);

        DashbordUserResponseDto dto = new DashbordUserResponseDto();
        dto.setNbreDmdTotal( totale);
        dto.setNbreDmdAnnule( annuler);
        dto.setNbreDmdValide( valider);
        dto.setNbreDmdRefuse( refuser);

        return dto;
    }


//    @Override
//    public List<DashbordUserResponseDto> dashbordUserResponseDto(Principal principal, EmployeRapportDto rapportDto, List<Dmd> dmds){
//     List<DashbordUserResponseDto> userResponseDtoList=new ArrayList<>();
//     List<RapportStatut> statuts = Arrays.asList(RapportStatut.values());
//        List<EmployeDmd> employeDmds = employeDmdService.getListEmployeDmdByDmd(dmds);
//
//
//     for(EmployeRapportDto employeRapportDto:){
//     DashbordUserResponseDto dashbordUserResponseDto=new DashbordUserResponseDto();
//     dashbordUserResponseDto.setNbreDmdTotal(employeDmds.size());
//     if(employeDmdService.getListEmployeDmdByDmd())
//
//
//     }
//
//
//        return  userResponseDtoList;
//    };



}
